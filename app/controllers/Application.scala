package controllers

import javax.inject.Inject

import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, AnyContent, Controller, EssentialAction}

import services.{Authenticated, UserService}

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider, userService: UserService) extends Controller {

  def secured(): EssentialAction = Authenticated.async {
    request =>
      Future.successful(Ok(views.html.index()))
  }

  def securedWithParam(id: Any): EssentialAction = Authenticated.async {
    request =>
      Future.successful(Ok(views.html.index()))
  }

  def unSecure(): Action[AnyContent] = Action.async { request =>
    if (request.session.get("email").isDefined) {
      userService.validateUser(request.session.get("email").get).map { valid =>
        if (valid) {
          Redirect("/welcome")
        } else {
          Ok(views.html.index())
        }
      }
    } else {
      Future.successful(Ok(views.html.index()))
    }
  }
}
