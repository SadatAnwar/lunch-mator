package persistence.repository

import models.RestaurantRow
import slick.driver.PostgresDriver.api._

class Restaurants(tag: Tag) extends Table[RestaurantRow](tag, Some("lunch_world"), "restaurants") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def name = column[String]("name")

  def website = column[String]("website")

  def description = column[String]("description")

  def userId = column[Int]("added_by_user_id")

  override def * = (id.?, name, website.?, description.?, userId) <> (RestaurantRow.tupled, RestaurantRow.unapply _)
}

object Restaurants {

  def searchRestaurant(name: String) = {
    restaurants.filter(_.name.toLowerCase like "%" + name.toLowerCase + "%").result
  }


  lazy val restaurants = TableQuery[Restaurants]

  def getAll = {
    restaurants.result
  }

  def addRestraurant(restaurant: RestaurantRow) = {
    restaurants += restaurant
  }

  def getRestaurantsById(restaurant_id: Int) = {
    restaurants.filter(_.id === restaurant_id).result.head
  }

  def getRestaurantsByName(name: String) = {
    restaurants.filter(_.name === name).result.headOption
  }
}
