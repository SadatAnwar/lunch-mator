package exceptions

import java.sql.Timestamp
import java.util.Date
import javax.inject.Inject

import models.{LunchRow, ParticipantRow, User}
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
    Participants.addParticipant(ParticipantRow(lunchId, user.id, new Timestamp(joined.getTime)))
  }

  def addUserToLunch(email: String, lunchId: Int) = usingDB {
    Users.getByEmail(email)
  }.flatMap { user =>
    val joined = new Date()
    Logger.info(s"adding user [$email] as participant for lunch [$lunchId]")
    usingDB(Participants.addParticipant(ParticipantRow(lunchId, user.id.getOrElse(-1), new Timestamp(joined.getTime))))
  }
}
