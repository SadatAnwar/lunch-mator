package repository

import models.Restaurant
import slick.driver.PostgresDriver.api._

class Restaurants(tag: Tag) extends Table[Restaurant](tag, "restaurants") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def name = column[String]("name")

  def location = column[String]("location")

  override def * = (id, name, location) <> (Restaurant.tupled, Restaurant.unapply _)
}

