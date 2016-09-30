package services

import com.google.inject.Inject
import models.{LunchTableRow, Restaurant, User}
import persistence.repository.{Restaurants, Users}
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future

class LunchTableMapper @Inject()(dbConfigDataProvider: DatabaseConfigProvider) extends Service(dbConfigDataProvider) {

  def map(lunchTableRow: LunchTableRow) = {
    val owner: Future[User] = usingDB {
      Users.findById(lunchTableRow.ownerId)
    }
    val restaurant: Future[Restaurant] = usingDB {
      Restaurants.getRestaurantsById(lunchTableRow.restaurantId)
    }

    // TODO: Make a LunchTable object here and return
  }
}
