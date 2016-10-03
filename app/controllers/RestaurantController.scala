package controllers

import com.google.inject.Inject
import models.Formats._
import models.Restaurant
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent}
import services.RestaurantService
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RestaurantController @Inject()(restaurantsService: RestaurantService) extends AuthenticatedBaseController {

  def listRestaurants(): Action[AnyContent] = Action.async { request =>
    val email: Option[String] = request.session.get("email")
    email match {
      case Some(e) => restaurantsService.getAllRestaurants.map(restaurants =>
        Ok(Json.toJson(restaurants))
      )
      case None => Future(Unauthorized)
    }
  }

  def createRestaurant() = Action.async(parse.json) { request =>
    val restaurant = request.body.as[Restaurant]
    restaurantsService.createNewRestaurant(restaurant).map {
      result => Created
    }.recoverWith {
      case e: Exception => Future(InternalServerError("Already exists " + e.getMessage))
    }
  }



  def findRestaurant(name: String) = Action.async {
    request =>
        withUser(request) {
        restaurantsService.getRestaurantByName(name).map(restaurants =>
          Ok(Json.toJson(restaurants))
        )
      }
  }
}
