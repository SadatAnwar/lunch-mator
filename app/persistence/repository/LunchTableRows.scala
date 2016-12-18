package persistence.repository

import com.github.tototoshi.slick.PostgresJodaSupport._
import models.{LunchRow, RestaurantRow}
import org.joda.time.DateTime
import slick.dbio.Effect.Read
import slick.driver.PostgresDriver.api._
import slick.jdbc.GetResult
import slick.profile.{SqlAction, SqlStreamingAction}

class LunchTableRows(tag: Tag) extends Table[LunchRow](tag, Some("lunch_world"), "lunch_tables") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def name = column[String]("name")

  def restaurantId = column[Int]("restaurant_id")

  def maxSize = column[Int]("max_size")

  def startTime = column[DateTime]("start_time")

  def anonymous = column[Boolean]("anonymous")

  override def * = (id.?, name.?, restaurantId, maxSize, startTime, anonymous) <> (LunchRow.tupled, LunchRow.unapply _)
}

object LunchTableRows {

  implicit val compoundLunchRestaurantSize = GetResult[(LunchRow, RestaurantRow, Int, Int)](r => (LunchRow(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<), RestaurantRow(r.<<, r.<<, r.<<, r.<<, r.<<), r.<<, r.<<))
  implicit val compoundLunchRestaurant = GetResult[(LunchRow, RestaurantRow)](r => (LunchRow(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<), RestaurantRow(r.<<, r.<<, r.<<, r.<<, r.<<)))

  lazy val lunchTableRows = TableQuery[LunchTableRows]

  def createLunch(lunchTableRow: LunchRow) = {
    lunchTableRows returning lunchTableRows.map(_.id) += lunchTableRow
  }

  def getAll = {
    lunchTableRows.result
  }

  def getLunchWithRestaurant(email: String, lunchId: Int): SqlStreamingAction[Vector[(LunchRow, RestaurantRow, Int, Int)], (LunchRow, RestaurantRow, Int, Int), Effect] = {
    sql"""SELECT
          lt.id,
          lt.name,
          lt.restaurant_id,
          lt.max_size,
          lt.start_time,
          lt.anonymous,

          rt.id,
          rt.name,
          rt.website,
          rt.description,
          rt.added_by_user_id,
          pt.joined,
          coalesce(pt.participants, 0) as participants
          FROM lunch_world.lunch_tables lt
            LEFT OUTER JOIN
              (SELECT
                 p.lunch_table_id,
                 max(CASE WHEN u.email = ${email} THEN 1 ELSE 0 END) AS joined,
                 count(*) AS participants
               FROM lunch_world.participants p
               JOIN lunch_world.users u ON u.id = p.user_id
               WHERE p.active = 'true'
               GROUP BY p.lunch_table_id) pt ON lt.id = pt.lunch_table_id
            JOIN lunch_world.restaurants rt ON rt.id = lt.restaurant_id
          WHERE lt.id = ${lunchId}
         ;
      """.as[(LunchRow, RestaurantRow, Int, Int)]
  }

  def filter() = {
    lunchTableRows.filter(_.id === 1).result
  }

  def getLunchForUserAfter(email: String, time: DateTime) = {
    sql"""SELECT
            lt.id,
            lt.name,
            lt.restaurant_id,
            lt.max_size,
            lt.start_time,
            lt.anonymous,

            rt.id,
            rt.name,
            rt.website,
            rt.description,
            rt.added_by_user_id
          FROM lunch_world.lunch_tables lt
            JOIN lunch_world.restaurants rt ON rt.id = lt.restaurant_id
            JOIN lunch_world.participants p ON lt.id = p.lunch_table_id
            JOIN lunch_world.users u on p.user_id = u.id
            WHERE lt.start_time > ${time}
            AND p.active = 'true'
            AND u.email = ${email};
      """.as[(LunchRow, RestaurantRow)]
  }

  def getLunchAfter(time: DateTime) = {
    val q = for {
      lunch <- lunchTableRows.filter(_.startTime > time)
      restaurant <- Restaurants.restaurants filter (lunch.restaurantId === _.id)
    } yield {
      (lunch, restaurant)
    }

    q.result
  }

  def getLunchWithOpenSpotsAfter(email: String, time: DateTime) = {
    sql"""SELECT
          lt.id,
          lt.name,
          lt.restaurant_id,
          lt.max_size,
          lt.start_time,
          lt.anonymous,

          rt.id,
          rt.name,
          rt.website,
          rt.description,
          rt.added_by_user_id,
          pt.joined,
          coalesce(pt.participants, 0) as participants
          FROM lunch_world.lunch_tables lt
            LEFT OUTER JOIN
              (SELECT
                 p.lunch_table_id,
                 max(CASE WHEN u.email = ${email} THEN 1 ELSE 0 END) AS joined,
                 count(*) AS participants
               FROM lunch_world.participants p
               JOIN lunch_world.users u ON u.id = p.user_id
               WHERE p.active = 'true'
               GROUP BY p.lunch_table_id) pt ON lt.id = pt.lunch_table_id
            JOIN lunch_world.restaurants rt ON rt.id = lt.restaurant_id
          WHERE lt.start_time > ${time}
          AND lt.max_size > coalesce(pt.participants, 0)
         ;
      """.as[(LunchRow, RestaurantRow, Int, Int)]
  }
}
