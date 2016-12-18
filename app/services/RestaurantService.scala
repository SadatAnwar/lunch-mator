package services

import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider

import mappers.RestaurantMapper
import models.{CreateRestaurantDto, RestaurantRow}
import persistence.repository.Restaurants

class RestaurantService @Inject()(val dbConfigDataProvider: DatabaseConfigProvider, val userService: UserService) {

  def searchRestaurant(name: String): Future[Seq[RestaurantRow]] = usingDB {
    Restaurants.searchRestaurant(name)
  }

  def createNewRestaurant(restaurant: RestaurantRow): Future[Int] = usingDB {
    Restaurants.addRestraurant(restaurant)
  }

  def createNewRestaurant(restaurantDto: CreateRestaurantDto, userName: String): Future[Int] = usingDB.async {
    userService.getUserByEmail(userName).map {
      user => RestaurantMapper.map(restaurantDto, user)
    }.map {
      restaurant => Restaurants.addRestraurant(restaurant)
    }
  }

  def getRandomRestaurant(): Future[RestaurantRow] = usingDB {
    Restaurants.selectRandomRestaurant()
  }

  def getAllRestaurants: Future[Seq[RestaurantRow]] = usingDB {
    Restaurants.getAll
  }

  def getRestaurantByName(name: String): Future[Option[RestaurantRow]] = usingDB {
    Restaurants.getRestaurantsByName(name)
  }
}
