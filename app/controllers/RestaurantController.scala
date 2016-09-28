package controllers

import com.google.inject.Inject
import models.Formats._
import models.Restaurant
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, Controller}
import services.RestaurantService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Should provide endpoints for
  * 1. Create a new restaurant
  * 2. Update a restaurant
  * 3. Delete restaurant (?) maybe only if super user or admin
  * 4.
  *
  * @param restaurantsService
  */
class RestaurantController @Inject()(restaurantsService: RestaurantService) extends Controller {

  def listRestaurants(): Action[AnyContent] = Action.async { request =>
    restaurantsService.getAllRestaurants.map(restaurants =>
      Ok(Json.toJson(restaurants))
    )
  }

  def createRestaurant() = Action.async(parse.json) { request =>
    val restaurant = request.body.as[Restaurant]
    restaurantsService.createNewRestaurant(restaurant).map {
      result => Created
    }.recoverWith {
      case e: Exception => Future(InternalServerError("Already exists " + e.getMessage))
    }
  }

  def findRestaurant(name: String) = Action.async { request =>
    restaurantsService.getRestaurantByName(name).map(restaurants =>
      Ok(Json.toJson(restaurants))
    )
  }
}
