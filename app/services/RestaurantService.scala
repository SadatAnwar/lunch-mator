package services

import javax.inject.Inject
import persistence.models.Restaurant
import persistence.repository.Restaurants
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery
import scala.concurrent.Future

class RestaurantService @Inject()(dbConfigDataProvider: DatabaseConfigProvider) extends Service(dbConfigDataProvider) {

  val restaurants = TableQuery[Restaurants]

  def createNewRestaurant(restaurant: Restaurant) = usingDB {
    restaurants += restaurant
  }

  def getAllRestaurants: Future[Seq[Restaurant]] = usingDB {
    restaurants.result
  }
}
