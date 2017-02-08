package services

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider

import com.google.inject.Inject
import exceptions.ParticipantService
import mappers.LunchMapper
import models.{CreateLunchDto, LunchRow, RestaurantRow}
import org.joda.time.DateTime
import persistence.repository.LunchTableRows

class LunchService @Inject()(implicit val dbConfigDataProvider: DatabaseConfigProvider, participantService: ParticipantService) extends Service
{
  def getLunchWithId(id: Int): Future[(LunchRow, RestaurantRow)] = usingDB {
    LunchTableRows.getLunchWithId(id)
  }

  def getAllLunchNotPast(email: String): Future[Vector[(LunchRow, RestaurantRow, Int, Int)]] = usingDB {
    LunchTableRows.getLunchWithOpenSpotsAfter(email, new DateTime().withDurationAdded(30 * 60 * 1000, -1))
  }

  def getLunchForUserNotPast(email: String): Future[Vector[(LunchRow, RestaurantRow)]] = usingDB {
    LunchTableRows.getLunchForUserAfter(email, new DateTime().withDurationAdded(30 * 60 * 1000, -1))
  }

  def getLunchDetail(email: String, lunchId: Int): Future[Vector[(LunchRow, RestaurantRow, Int, Int)]] = usingDB {
    LunchTableRows.getLunchWithRestaurant(email, lunchId)
  }

  def createLunch(email: String, lunchDto: CreateLunchDto): Future[Int] =
  {
    Logger.info(s"User:[$email created lunch with Name: [${lunchDto.lunchName.getOrElse("")}]]")
    usingDB {
      val lunch = LunchMapper.map(lunchDto)
      LunchTableRows.createLunch(lunch)
    }.flatMap { lunchId =>
      participantService.addUserToLunch(email, lunchId)
      Future.successful(lunchId)
    }
  }
}
