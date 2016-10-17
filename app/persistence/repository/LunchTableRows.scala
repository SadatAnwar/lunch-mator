package persistence.repository

import java.sql.Timestamp
import java.util.Date
import models.{Lunch, LunchRow, ParticipantRow}
import play.api.Logger
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

class LunchTableRows(tag: Tag) extends Table[LunchRow](tag, Some("lunch_world"), "lunch_tables") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def restaurantId = column[Int]("restaurant_id")

  def maxSize = column[Int]("max_size")

  def startTime = column[Timestamp]("start_time")

  def anonymous = column[Boolean]("anonymous")

  override def * = (id.?, restaurantId, maxSize, startTime, anonymous) <> (LunchRow.tupled, LunchRow.unapply _)
}

object LunchTableRows {

  lazy val lunchTableRows = TableQuery[LunchTableRows]

  def getLunchTableById(id: Int) = {
    val q = for {
      lunch <- lunchTableRows.filter(_.id === id).result.head
      restaurant <- Restaurants.restaurants.filter(_.id === lunch.restaurantId).result.head
    } yield {
      (lunch, restaurant)
    }
    q.map {
      (a) => Lunch(a._2, a._1.maxSize, a._1.startTime)
    }
  }

  def createLunch(lunchTableRow: LunchRow) = {
    lunchTableRows += lunchTableRow
  }

  def createLunchII(lunchTableRow: LunchRow) = {
    lunchTableRows returning lunchTableRows.map(_.id) into {
      (lunch, lunchId) =>
        val joined = new Date()
        Logger.info(s"adding user [1] as participant for lunch [$lunchId]")
        Participants.addParticipant(ParticipantRow(lunchId, 1, new Timestamp(joined.getTime)))
    } += lunchTableRow
  }

  var getAll = {
    lunchTableRows.result
  }
}
