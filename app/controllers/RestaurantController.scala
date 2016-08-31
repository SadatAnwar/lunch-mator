package controllers

import javax.inject.Inject
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import services.RestaurantService

class RestaurantController @Inject()(restaurantsService: RestaurantService) extends Controller {

  def listRestaurants() = Action { request =>
    val restuarants = restaurantsService.getAllRestaurants
    Ok(Json.toJson(restuarants))
  }
}
