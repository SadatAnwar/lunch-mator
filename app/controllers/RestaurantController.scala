package controllers

import javax.inject.Inject
import persistence.models.Formats._
import persistence.models.Restaurant
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, Controller}
import services.RestaurantService
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Should provide endpoints for
  * 1. Create a new restaurant
  * 2. Update a restaurant
  * 3. Delete restaurant (?) maybe only if super user or admin
  * 4.
  * @param restaurantsService
  */
class RestaurantController @Inject()(restaurantsService: RestaurantService) extends Controller {

  def listRestaurants(): Action[AnyContent] = Action.async { request =>
    restaurantsService.getAllRestaurants.map(restaurants =>
      Ok(Json.toJson(restaurants))
    )
  }

  def createRestaurant() = Action(parse.json) { request =>
    val restaurant = request.body.as[Restaurant]
    restaurantsService.createNewRestaurant(restaurant)
    Created
  }
}
