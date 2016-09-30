package persistence.repository

import java.sql.Timestamp
import models.LunchTableRow
import slick.driver.PostgresDriver.api._

class LunchTableRows(tag: Tag) extends Table[LunchTableRow](tag, Some("lunch_world"), "lunch_tables") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def ownerId = column[Int]("owner_id")

  def restaurantId = column[Int]("restaurant_id")

  def maxSize = column[Int]("max_size")

  def startTime = column[Timestamp]("start_time")

  def anonymous = column[Boolean]("anonymous")

  override def * = (id.?, ownerId, restaurantId, maxSize, startTime, anonymous) <> (LunchTableRow.tupled, LunchTableRow.unapply _)
}

object LunchTableRows {

  lazy val lunchTableRows = TableQuery[LunchTableRows]

  def getListOfTablesById(id: Int) = {
    val q = for {
      lunch <- lunchTableRows.filter(_.id === id)
      restaurant <- Restaurants.restaurants
      owner <- Users.users
      if owner.id === lunch.ownerId && restaurant.id === lunch.restaurantId
    } yield {
      (lunch, owner, restaurant)
    }
    q.result.head
  }

  def saveLunchTable(lunchTableRow: LunchTableRow) = {
    lunchTableRows += lunchTableRow
  }

  var getAll = {
    lunchTableRows.result
  }
}
