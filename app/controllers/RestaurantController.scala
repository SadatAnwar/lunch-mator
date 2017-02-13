package controllers

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.json._
import play.api.mvc.{Action, Controller, EssentialAction}

import com.google.inject.Inject
import exceptions.RestaurantNotFoundException
import models.CreateRestaurantDto
import models.Formats._
import services.{Authenticated, RestaurantService}

class RestaurantController @Inject()(restaurantsService: RestaurantService) extends Controller
{

  def listRestaurants(): EssentialAction = Authenticated.async { request =>
    restaurantsService.getAllRestaurants.map(restaurants =>
      Ok(Json.toJson(restaurants))
    )
  }

  def addRestaurant(): Action[JsValue] = Authenticated.async(parse.json) { request =>
    val restaurant = request.body.as[CreateRestaurantDto]
    restaurantsService.createNewRestaurant(restaurant, request.username).map {
      result => Created(Json.toJson(result))
    }
  }

  def findRestaurant(name: String): EssentialAction = Authenticated.async { request =>
    restaurantsService.getRestaurantByName(name).map(restaurant =>
      Ok(Json.toJson(restaurant))
    )
  }

  def loadRestaurant(id: Int): EssentialAction = Authenticated.async { request =>
    restaurantsService.loadRestaurant(id).map { restaurant =>
      Ok(Json.toJson(restaurant.getOrElse(throw RestaurantNotFoundException(id))))
    }
  }

  def searchRestaurant(name: String): EssentialAction = Authenticated.async { request =>
    restaurantsService.searchRestaurant(name).map { restaurant =>
      Ok(Json.toJson(restaurant))
    }
  }

  def getRandomRestaurant: EssentialAction = Authenticated.async { request =>
    restaurantsService.getRandomRestaurant().map { restaurant =>
      Ok(Json.toJson(restaurant))
    }
  }
}
