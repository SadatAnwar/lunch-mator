package controllers

import com.google.inject.Inject
import models.Formats._
import models.RestaurantDto
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import services.{Authenticated, RestaurantService}
import scala.concurrent.ExecutionContext.Implicits.global

class RestaurantController @Inject()(restaurantsService: RestaurantService) extends Controller {

  def listRestaurants() = Authenticated.async {
    implicit request =>
      restaurantsService.getAllRestaurants.map(restaurants =>
        Ok(Json.toJson(restaurants))
      )
  }

  def addRestaurant() = Authenticated.async(parse.json) { request =>
    val restaurant = request.body.as[RestaurantDto]
    restaurantsService.createNewRestaurant(restaurant, request.username).map {
      result => Created
    }
  }

  def findRestaurant(name: String) = Authenticated.async {
    implicit request =>
      restaurantsService.getRestaurantByName(name).map(restaurant =>
        Ok(Json.toJson(restaurant))
      )
  }

  def searchRestaurant(name: String) = Authenticated.async {
    implicit request =>
      restaurantsService.searchRestaurant(name).map(restaurant =>
        Ok(Json.toJson(restaurant))
      )
  }
}
