package persistence.repository

import com.github.tototoshi.slick.H2JodaSupport._
import models.{Lunch, LunchRow}
import org.joda.time.DateTime
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global

class LunchTableRows(tag: Tag) extends Table[LunchRow](tag, Some("lunch_world"), "lunch_tables") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def restaurantId = column[Int]("restaurant_id")

  def maxSize = column[Int]("max_size")

  def startTime = column[DateTime]("start_time")

  def anonymous = column[Boolean]("anonymous")

  override def * = (id.?, restaurantId, maxSize, startTime, anonymous) <> (LunchRow.tupled, LunchRow.unapply _)
}

object LunchTableRows {

  lazy val lunchTableRows = TableQuery[LunchTableRows]

  def getLunchTableById(id: Int) = {
    val q = for {
      lunch <- lunchTableRows.result.head
      restaurant <- Restaurants.restaurants.filter(_.id === lunch.restaurantId).result.head
    } yield {
      (lunch, restaurant)
    }
    q.map {
      (a) => Lunch(a._1.id.getOrElse(-1), a._2, a._1.maxSize, a._1.startTime, a._1.anonymous)
    }
  }

  def createLunch(lunchTableRow: LunchRow) = {
    lunchTableRows returning lunchTableRows.map(_.id) += lunchTableRow
  }

  def getAll = {
    lunchTableRows.result
  }

  def getLunchWithRestaurant() = {
    val q = for {
      (lunch, restaurant) <- lunchTableRows join Restaurants.restaurants on (_.restaurantId === _.id)
    } yield{
      (lunch, restaurant)
    }

    q.result
  }

  def filter() = {
    lunchTableRows.filter(_.id === 1).result
  }
}
