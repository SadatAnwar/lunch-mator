package services

import java.util.Date

import com.google.inject.Inject
import exceptions.{ParticipantService, UserNotFoundException}
import mappers.LunchMapper
import models.{CreateLunchDto, LunchRow, ParticipantRow, RestaurantRow}
import org.joda.time.DateTime
import persistence.repository.{LunchTableRows, Participants, Users}
import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LunchService @Inject()(implicit val dbConfigDataProvider: DatabaseConfigProvider, participantService: ParticipantService) extends Service {

  def getAllLunchNotPast: Future[Vector[(LunchRow, RestaurantRow, Int)]] = usingDB {
    LunchTableRows.getLunchWithOpenSpotsAfter(new DateTime().withDurationAdded(30 * 60 * 1000, -1))
  }

  def getLunchDetail(lunchId: Int): Future[(LunchRow, RestaurantRow)] = usingDB {
    LunchTableRows.getLunchWithRestaurant(lunchId)
  }

  def createLunch(email: String, lunchDto: CreateLunchDto) = usingDB {
    val lunch = LunchMapper.map(lunchDto)
    val lunchId = LunchTableRows.createLunch(lunch)
    lunchId.flatMap(singUpCreatorForLunch(email, _))
  }

  private def singUpCreatorForLunch(email: String, lunchId: Int) = {
    Users.getByEmail(email).flatMap { user =>
      val joined = new Date()
      Logger.info(s"adding user [$user] as participant for lunchId [$lunchId]")
      Participants.addParticipant(ParticipantRow(lunchId, user.id.getOrElse(throw new UserNotFoundException(user)), new DateTime(joined.getTime)))
    }
  }
}
