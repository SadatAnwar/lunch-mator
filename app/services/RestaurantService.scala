package services

import javax.inject.Inject

import models.Restaurant
import play.api.db.slick.DatabaseConfigProvider
import repository.Restaurants
import slick.lifted.TableQuery
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future

class RestaurantService @Inject()(dbConfigDataProvider: DatabaseConfigProvider) extends Service(dbConfigDataProvider) {
  val restaurants = TableQuery[Restaurants]

  def getAllRestaurants: Future[Seq[Restaurant]] = usingDB {
    restaurants.take(10).result
  }
}
