package repository

import models.Restaurant
import slick.driver.PostgresDriver.api._

class Restaurants(tag: Tag) extends Table[Restaurant](tag, "Restaurant") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def name = column[String]("restaurant_name")

  def location = column[Int]("location")

  override def * = (id.?, name, location) <> (Restaurant.tupled, Restaurant.unapply _)
}

