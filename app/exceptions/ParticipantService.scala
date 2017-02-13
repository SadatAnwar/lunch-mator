package exceptions

import java.util.Date
import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider

import models._
import org.joda.time.DateTime
import persistence.repository.{Participants, Users}
import services.{LunchNotFoundException, UserService, usingDB}

class ParticipantService @Inject()(implicit dbConfigDataProvider: DatabaseConfigProvider, userService: UserService)
{

  def addUserToLunch(user: User, lunch: LunchRow): Future[Int] = usingDB {
    val joined = new Date()
    val lunchId = lunch.id.getOrElse(throw new LunchNotFoundException(lunch))
    Logger.info(s"adding user [$user] as participant for lunch [$lunch]")
    Participants.addParticipant(ParticipantRow(lunchId, user.id, new DateTime(joined.getTime)))
  }

  def addUserToLunch(email: String, lunchId: Int): Future[Int] = usingDB {
    Users.getByEmail(email).flatMap { user =>
      val joined = new DateTime()
      Logger.info(s"adding user [${user.firstName}] as participant for lunch [$lunchId]")
      val participant = ParticipantRow(lunchId, user.id.get, joined)
      Participants.addParticipant(participant)
    }
  }

  def removeUserFromLunch(email: String, lunchId: Int): Future[Int] = usingDB {
    Users.getByEmail(email).flatMap { user =>
      Logger.info(s"removing user [${user.firstName}] from lunch [$lunchId]")
      Participants.deactivateParticipantForLunch(user.id.get, lunchId)
    }
  }

  def getParticipationDetails(lunchId: Int): Future[Seq[(ParticipantRow, UserRow, LunchRow, RestaurantRow)]] = usingDB {
    Participants.getParticipantsForLunch(lunchId)
  }
}
