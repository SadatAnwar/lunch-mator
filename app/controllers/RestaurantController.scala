package controllers

import javax.inject.Inject
import persistence.models.Formats._
import persistence.models.Restaurant
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, Controller}
import services.RestaurantService
import scala.concurrent.ExecutionContext.Implicits.global

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
