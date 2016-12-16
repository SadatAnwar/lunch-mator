package controllers

import scala.concurrent.Future

import play.api.Logger
import play.api.mvc.{Action, AnyContent, Controller}

import com.google.inject.Inject
import services.AuthenticationService

class AuthenticationController @Inject()(authenticationService: AuthenticationService) extends Controller {

  def logOut(): Action[AnyContent] = Action.async { request =>
    Logger.info(s"Logging out User:{${request.session.get("email").getOrElse("NO USER")}}")
    Future.successful(Redirect("/login").withNewSession)
  }
}

