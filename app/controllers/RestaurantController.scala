package controllers

import com.google.inject.Inject
import models.Formats._
import models.Restaurant
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, Controller}
import services.{Authenticated, RestaurantService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RestaurantController @Inject()(restaurantsService: RestaurantService) extends Controller {

  def listRestaurants() = Authenticated.async {
    implicit request =>
      restaurantsService.getAllRestaurants.map(restaurants =>
        Ok(Json.toJson(restaurants))
      )
  }

  def createRestaurant() = Authenticated.async(parse.json) { request =>
    val restaurant = request.body.as[Restaurant]
    restaurantsService.createNewRestaurant(restaurant).map {
      result => Created
    }.recoverWith {
      case e: Exception => Future(InternalServerError("Already exists " + e.getMessage))
    }
  }

  def findRestaurant(name: String) = Authenticated.async {
    implicit request =>
      restaurantsService.getRestaurantByName(name).map(restaurant =>
        Ok(Json.toJson(restaurant))
      )
  }
}
