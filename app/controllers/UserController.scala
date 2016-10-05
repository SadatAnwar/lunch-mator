package controllers

import com.google.inject.Inject
import models.Formats._
import models.User
import play.api.libs.json._
import play.api.mvc.Controller
import services.{Authenticated, UserService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserController @Inject()(userService: UserService) extends Controller {

  def getAllUsers = Authenticated.async {
    request =>
      userService.getAllUsers.map(restaurants =>
        Ok(Json.toJson(restaurants))
      )
  }

  def createUser() = Authenticated.async(parse.json) { request =>
    val user = request.body.as[User]
     userService.addUser(user).map {
      result => Created
    }.recoverWith {
      case e: Exception => Future(InternalServerError("Already exists " + e.getMessage))
    }
  }
}
