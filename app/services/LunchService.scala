package services

import scala.concurrent.{ExecutionContext, Future}

import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider

import actors.{LunchReminderMessage, NewLunchCreatedMessage}
import com.google.inject.Inject
import mappers.LunchMapper
import models._
import org.joda.time.DateTime
import persistence.repository.LunchTableRows

class LunchService @Inject()(scheduler: MessageService)(implicit ec: ExecutionContext, implicit val dbConfigDataProvider: DatabaseConfigProvider) extends Service
{

  def getLunchWithOpenSpotsAfter(user: UserRow): Future[Vector[(LunchRow, RestaurantRow, Int, Int)]] = usingDB {
    LunchTableRows.getLunchWithOpenSpotsAfter(user.email, new DateTime().withDurationAdded(30 * 60 * 1000, -1))
  }

  def getAllActiveLunch: Future[Seq[LunchDetail]] = usingDB {
    LunchTableRows.getLunchAfter(DateTime.now())
  }

  def getLunchForUserNotPast(email: String): Future[Vector[(LunchRow, RestaurantRow)]] = usingDB {
    LunchTableRows.getLunchForUserAfter(email, new DateTime().withDurationAdded(30 * 60 * 1000, -1))
  }

  def getLunchDetail(email: String, lunchId: Int): Future[Vector[(LunchRow, RestaurantRow, Int, Int)]] = usingDB {
    LunchTableRows.getLunchWithRestaurant(email, lunchId)
  }

  def getLunchAndRestaurant(id: Int): Future[(LunchRow, RestaurantRow)] = usingDB {
    LunchTableRows.getLunchDetailsWithId(id)
  }

  def getLunch(id: Int): Future[LunchRow] = usingDB {
    LunchTableRows.getLunchWithId(id)
  }

  def deactivateLunch(lunchId: Int): Future[Int] = usingDB {
    LunchTableRows.deactivateLunch(lunchId)
  }

  def createLunch(user: UserRow, lunchDto: CreateLunchDto): Future[Int] =
  {
    Logger.info(s"User:[${user.firstName} ${user.lastName}] | RestaurantId:[${lunchDto.restaurantId}] | New lunch created ")

    usingDB {
      val lunch = LunchMapper.map(lunchDto)
      LunchTableRows.createLunch(lunch)
    }.flatMap { lunchId =>

      scheduler.publishMessage(NewLunchCreatedMessage(lunchId, user))
      val remindAt = new DateTime(lunchDto.startTime).minusMinutes(10)
      scheduler.scheduleMessage(LunchReminderMessage(lunchId), remindAt)

      Future.successful(lunchId)
    }
  }
}
