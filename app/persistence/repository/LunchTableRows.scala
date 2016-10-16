package persistence.repository

import java.sql.Timestamp
import models.{Lunch, LunchTableRow}
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

class LunchTableRows(tag: Tag) extends Table[LunchTableRow](tag, Some("lunch_world"), "lunch_tables") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def restaurantId = column[Int]("restaurant_id")

  def maxSize = column[Int]("max_size")

  def startTime = column[Timestamp]("start_time")

  def anonymous = column[Boolean]("anonymous")

  override def * = (id.?, restaurantId, maxSize, startTime, anonymous) <> (LunchTableRow.tupled, LunchTableRow.unapply _)
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
      (a) => Lunch(restaurant = a._2, size = 6)
    }
  }

  def createLunch(lunchTableRow: LunchTableRow) = {
    lunchTableRows += lunchTableRow
  }

  var getAll = {
    lunchTableRows.result
  }
}
