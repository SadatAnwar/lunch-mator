package services

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import repository.Restaurants
import slick.driver.JdbcDriver
import slick.lifted.TableQuery

class RestaurantService @Inject()(dbConfigDataProvider: DatabaseConfigProvider) {

  val db = dbConfigDataProvider[JdbcDriver]

  def getAllRestaurants = {
    TableQuery[Restaurants].take(10)
  }
}
