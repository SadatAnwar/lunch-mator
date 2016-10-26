package persistence.repository

import com.github.tototoshi.slick.PostgresJodaSupport._
import models.ParticipantRow
import org.joda.time.DateTime
import play.api.Logger
import slick.driver.PostgresDriver.api._
import slick.lifted.Tag

class Participants(tag: Tag) extends Table[ParticipantRow](tag, Some("lunch_world"), "participants") {

  def lunchId = column[Int]("lunch_table_id")

  def userId = column[Int]("user_id")

  def joinedAt = column[DateTime]("joined_at")

  override def * = (lunchId, userId, joinedAt) <> (ParticipantRow.tupled, ParticipantRow.unapply _)
}

object Participants {

  lazy val participants = TableQuery[Participants]

  def addParticipant(participantRow: ParticipantRow) = {
    Logger.info(s"Inserting $participantRow into table")
    participants += participantRow
  }

  def getParticipantsForLunch(lunchId: Int) = {
    val q = for {
      lunch <- LunchTableRows.lunchTableRows.filter(_.id === lunchId)
      restaurant <- Restaurants.restaurants.filter(_.id === lunch.restaurantId)
      (participants, user) <- participants.filter(_.lunchId === lunchId) join Users.users on (_.userId === _.id)
    } yield {
      (participants, user, lunch, restaurant)
    }
    q.result
  }
}
