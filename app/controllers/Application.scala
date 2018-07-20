package controllers

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, AnyContent, Controller, EssentialAction}

import services.{AuthenticatedService, UserService}

class Application @Inject()(userService: UserService)(implicit db: DatabaseConfigProvider, ec: ExecutionContext) extends AuthenticatedService with Controller {

  def secured(): EssentialAction = async {
    request =>
      Future.successful(Ok(views.html.index()))
  }

  def securedWithParam(id: Any): EssentialAction = async {
    request =>
      Logger.info(s"Secured route:[$id] requested")
      Future.successful(Ok(views.html.index()))
  }

  def unSecure(): Action[AnyContent] = Action.async { request =>
    val params = request.queryString.map { case (k, v) => k -> v.mkString }
    val origin = params.getOrElse("origin", "/welcome")
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
