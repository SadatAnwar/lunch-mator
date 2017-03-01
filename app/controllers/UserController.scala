package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json._
import play.api.mvc.{Action, Controller, EssentialAction}

import com.google.inject.Inject
import models.Formats._
import models.UserRow
import services.{AuthenticatedService, UserService}

class UserController @Inject()(userService: UserService)(implicit db: DatabaseConfigProvider) extends AuthenticatedService with Controller
{

  def getAllUsers: EssentialAction = async {
    request =>
      userService.getAllUsers.map(restaurants =>
        Ok(Json.toJson(restaurants))
      )
  }

  def createUser(): Action[JsValue] = async(parse.json) { request =>
    val user = request.body.as[UserRow]
    userService.addUser(user).map {
      result => Created
    }.recoverWith {
      case e: Exception => Future(InternalServerError("Already exists " + e.getMessage))
    }
  }
}
