package exceptions

import java.util.Date
import javax.inject.Inject

import mappers.ParticipantMapper
import models.{LunchRow, ParticipantRow, User}
import org.joda.time.DateTime
import persistence.repository.{Participants, Users}
import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider
import services.{LunchNotFoundException, UserService, usingDB}

import scala.concurrent.ExecutionContext.Implicits.global

class ParticipantService @Inject()(implicit dbConfigDataProvider: DatabaseConfigProvider, userService: UserService) {

  def addUserToLunch(user: User, lunch: LunchRow) = usingDB {
    val joined = new Date()
    val lunchId = lunch.id.getOrElse(throw new LunchNotFoundException(lunch))
    Logger.info(s"adding user [$user] as participant for lunch [$lunch]")
    Participants.addParticipant(ParticipantRow(lunchId, user.id, new DateTime(joined.getTime)))
  }

  def addUserToLunch(email: String, lunchId: Int) = usingDB {
    Users.getByEmail(email)
  }.flatMap { user =>
    val joined = new Date()
    Logger.info(s"adding user [$email] as participant for lunch [$lunchId]")
    usingDB(Participants.addParticipant(ParticipantRow(lunchId, user.id.getOrElse(-1), new DateTime(joined.getTime))))
  }

  def removeUserFromLunch(email: String, lunchId: Int) = usingDB {
    Users.getByEmail(email)
  }.flatMap { user =>
    Logger.info(s"adding user [$email] as participant for lunch [$lunchId]")
    usingDB(Participants.deactivateParticipantForLunch(user.id.getOrElse(-1), lunchId))
  }

  def getParticipants(lunchId: Int) = usingDB {
    Participants.getParticipantsForLunch(lunchId)
  }.map {
    participants => participants.map { p =>
      ParticipantMapper.map(p._1, p._2)
    }
  }
}
