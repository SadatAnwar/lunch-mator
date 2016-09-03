package persistence.repository

import persistence.models.Restaurant
import slick.driver.PostgresDriver.api._

class Restaurants(tag: Tag) extends Table[Restaurant](tag, Some("lunch_world"), "restaurants") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def name = column[String]("name")

  def website = column[String]("website")

  override def * = (id.?, name, website) <> (Restaurant.tupled, Restaurant.unapply _)
}
