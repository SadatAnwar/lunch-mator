package controllers

import javax.inject.Inject
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import services.RestaurantService
import scala.concurrent.ExecutionContext.Implicits.global
import models.Formats._

class RestaurantController @Inject()(restaurantsService: RestaurantService) extends Controller {

  def listRestaurants() = Action.async { request =>
    restaurantsService.getAllRestaurants.map(restaurants =>
      Ok(Json.toJson(restaurants))
        .withHeaders(CONTENT_TYPE -> "application/json")
    )
  }
}
