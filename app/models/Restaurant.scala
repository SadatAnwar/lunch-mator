package models

import slick.driver.PostgresDriver.api._

case class Restaurant(id: Option[Int], name: String, location: Int)

class Restaurants(tag: Tag) extends Table[Restaurant](tag, "Restaurant") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def name = column[String]("restaurant_name")

  def location = column[Int]("location")

  override def * = (id.?, name, location) <> (Restaurant.tupled, Restaurant.unapply _)
}
