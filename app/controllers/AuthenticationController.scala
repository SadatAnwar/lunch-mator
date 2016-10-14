package controllers

import com.google.inject.Inject
import models.Formats._
import models.{NewUserDto, UserIdentity}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.{AuthenticationService, UserIdentityService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AuthenticationController @Inject()(authenticationService: AuthenticationService) extends Controller {

  def signUp() = Action.async(parse.json) { request =>
    authenticationService.signUp(request.body.as[NewUserDto]).map {
      userIdentityStore => Created(Json.toJson(UserIdentityService.map(userIdentityStore)))
    }.recoverWith {
      case e: Exception => Future(InternalServerError("NewUser with email already exists " + e.getMessage))
    }
  }

  def authenticate() = Action.async(parse.json) { request =>
    val identity: UserIdentity = request.body.as[UserIdentity]
    authenticationService.getUserByEmail(identity.email).map {
      user =>
        UserIdentityService.validatePassword(identity.password, user.password).fold(
          success => Ok.withSession("email" -> identity.email),
          error => InternalServerError
        )
    }
  }
}
