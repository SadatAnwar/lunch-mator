package services

import java.sql.Timestamp
import java.util.Date
import com.google.inject.Inject
import exceptions.{ParticipantService, UserNotFoundException}
import mappers.LunchTableMapper
import models.{CreateLunchDto, ParticipantRow}
import persistence.repository.{LunchTableRows, Participants, Users}
import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global

class LunchService @Inject()(implicit val dbConfigDataProvider: DatabaseConfigProvider, participantService: ParticipantService) extends Service {

  def getAllLunchTables = usingDB {
    LunchTableRows.getLunchWithRestaurant()
  }

  def getLunchById(id: Int) = usingDB {
    LunchTableRows.getLunchTableById(id)
  }

  def createLunch(email: String, lunchDto: CreateLunchDto) = usingDB {
    val lunch = LunchTableMapper.map(lunchDto)
    val lunchId = LunchTableRows.createLunch(lunch)
    lunchId.flatMap(singUpCreatorForLunch(email, _))
  }

  private def singUpCreatorForLunch(email: String, lunchId: Int) = {
    Users.getByEmail(email).flatMap { user =>
      val joined = new Date()
      Logger.info(s"adding user [$user] as participant for lunchId [$lunchId]")
      Participants.addParticipant(ParticipantRow(lunchId, user.id.getOrElse(throw new UserNotFoundException(user)), new Timestamp(joined.getTime)))
    }
  }
}
