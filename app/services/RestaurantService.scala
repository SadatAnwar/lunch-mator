package services

import javax.inject.Inject
import mappers.RestaurantMapper
import models.{RestaurantDto, RestaurantRow}
import persistence.repository.Restaurants
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RestaurantService @Inject()(val dbConfigDataProvider: DatabaseConfigProvider, val userService: UserService) {

  def searchRestaurant(name: String) = usingDB {
    Restaurants.searchRestaurant(name)
  }

  def createNewRestaurant(restaurant: RestaurantRow) = usingDB {
    Restaurants.addRestraurant(restaurant)
  }

  def createNewRestaurant(restaurantDto: RestaurantDto, userName: String) = usingDB.async {
    userService.getUserByEmail(userName).map {
      user => RestaurantMapper.map(restaurantDto, user)
    }.map {
      restaurant => Restaurants.addRestraurant(restaurant)
    }
  }

  def getAllRestaurants: Future[Seq[RestaurantRow]] = usingDB {
    Restaurants.getAll
  }

  def getRestaurantByName(name: String): Future[Option[RestaurantRow]] = usingDB {
    Restaurants.getRestaurantsByName(name)
  }
}
