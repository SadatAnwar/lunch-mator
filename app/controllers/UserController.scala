package controllers

import com.google.inject.Inject
import models.Formats._
import models.User
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, Controller}
import services.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserController @Inject()(userService: UserService) extends Controller {

  def getAllUsers: Action[AnyContent] = Action.async { request =>
    userService.getAllUsers.map(restaurants =>
      Ok(Json.toJson(restaurants))
    )
  }

  def createUser() = Action.async(parse.json) { request =>
    val user = request.body.as[User]
     userService.addUser(user).map {
      result => Created
    }.recoverWith {
      case e: Exception => Future(InternalServerError("Already exists " + e.getMessage))
    }
  }
}
