package controllers

import scala.concurrent.Future

import play.api.Logger
import play.api.mvc.{Action, AnyContent, Controller}

import com.google.inject.Inject

class AuthenticationController @Inject() extends Controller {

  def logOut(): Action[AnyContent] = Action.async { request =>
    Logger.info(s"Log Out:[${request.session.get("email").getOrElse("NO USER")}]")
    Future.successful(Redirect("/login").withNewSession)
  }
}

