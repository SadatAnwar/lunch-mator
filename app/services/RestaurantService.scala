package services

import javax.inject.Inject

import models.{RestaurantRow, UserRow}
import persistence.repository.Restaurants
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Future

class RestaurantService @Inject()(val dbConfigDataProvider: DatabaseConfigProvider) {

  def searchRestaurant(name: String) = usingDB {
    Restaurants.searchRestaurant(name)
  }

  def createNewRestaurant(restaurant: RestaurantRow) = usingDB {
    Restaurants.addRestraurant(restaurant)
  }

  def getAllRestaurants: Future[Seq[RestaurantRow]] = usingDB {
    Restaurants.getAll
  }

  def getRestaurantByName(name: String): Future[Option[RestaurantRow]] = usingDB {
    Restaurants.getRestaurantsByName(name)
  }
}
