package services

import scala.concurrent.{ExecutionContext, Future}

import play.api.Logger

import actors.LunchReminderActor.LunchReminderMessage
import com.google.inject.Inject
import exceptions.ParticipantService
import mappers.LunchMapper
import models.{CreateLunchDto, LunchRow, RestaurantRow}
import org.joda.time.DateTime
import persistence.repository.LunchTableRows
import scheduler.Scheduler

class LunchService @Inject()(participantService: ParticipantService, scheduler: Scheduler)(implicit ec: ExecutionContext)
{

  def getAllLunchNotPast(email: String): Future[Vector[(LunchRow, RestaurantRow, Int, Int)]] = usingDB {
    LunchTableRows.getLunchWithOpenSpotsAfter(email, new DateTime().withDurationAdded(30 * 60 * 1000, -1))
  }

  def getLunchForUserNotPast(email: String): Future[Vector[(LunchRow, RestaurantRow)]] = usingDB {
    LunchTableRows.getLunchForUserAfter(email, new DateTime().withDurationAdded(30 * 60 * 1000, -1))
  }

  def getLunchDetail(email: String, lunchId: Int): Future[Vector[(LunchRow, RestaurantRow, Int, Int)]] = usingDB {
    LunchTableRows.getLunchWithRestaurant(email, lunchId)
  }

  def getLunchAndRestaurant(id: Int): Future[(LunchRow, RestaurantRow)] = usingDB {
    LunchTableRows.getLunchWithId(id)
  }

  def createLunch(email: String, lunchDto: CreateLunchDto): Future[Int] =
  {
    Logger.info(s"New lunch created | User:[$email] | RestaurantId:[${lunchDto.restaurantId}]")

    usingDB {
      val lunch = LunchMapper.map(lunchDto)
      LunchTableRows.createLunch(lunch)
    }.flatMap { lunchId =>
      participantService.addUserToLunch(email, lunchId)

      val remindAt = new DateTime(lunchDto.startTime).minusMinutes(10)
      scheduler.scheduleMessage(LunchReminderMessage(lunchId), remindAt)

      Future.successful(lunchId)
    }
  }
}
