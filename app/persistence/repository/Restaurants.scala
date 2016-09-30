package persistence.repository

import models.Restaurant
import slick.driver.PostgresDriver.api._

class Restaurants(tag: Tag) extends Table[Restaurant](tag, Some("lunch_world"), "restaurants") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def name = column[String]("name")

  def website = column[String]("website")

  override def * = (id.?, name, website) <> (Restaurant.tupled, Restaurant.unapply _)
}

object Restaurants {

  lazy val restaurants = TableQuery[Restaurants]

  def getAll = {
    restaurants.result
  }

  def addRestraurant(restaurant: Restaurant) = {
    restaurants += restaurant
  }

  def getRestaurantsById(restaurant_id: Int) = {
    restaurants.filter(_.id === restaurant_id).result.head
  }

  def getRestaurantsByName(name: String) = {
    restaurants.filter(_.name === name).result.headOption
  }
}
