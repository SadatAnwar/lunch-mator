package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.mvc.{Action, Controller}

import com.google.inject.Inject
import models.Formats._
import models.UserIdentity
import services.{AuthenticationService, UserIdentityService}

class AuthenticationController @Inject()(authenticationService: AuthenticationService) extends Controller {

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

  def logOut() = Action.async { request =>
    Future.successful(Redirect("/login").withNewSession)
  }
}

