package controllers

import com.google.inject.Inject
import mappers.RestaurantMapper
import models.Formats._
import models.RestaurantDto
import play.api.libs.json._
import play.api.mvc.Controller
import services.{Authenticated, RestaurantService, UserService}
import scala.concurrent.ExecutionContext.Implicits.global

class RestaurantController @Inject()(restaurantsService: RestaurantService, userService: UserService) extends Controller {

  def listRestaurants() = Authenticated.async {
    implicit request =>
      restaurantsService.getAllRestaurants.map(restaurants =>
        Ok(Json.toJson(restaurants))
      )
  }

  def addRestaurant() = Authenticated.async(parse.json) { request =>
    val restaurant = request.body.as[RestaurantDto]
    userService.getUserByEmail(request.username).map {
      user => RestaurantMapper.map(restaurant, user)
    }.flatMap {
      restaurant => restaurantsService.createNewRestaurant(restaurant).map {
        result => Created
      }
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
