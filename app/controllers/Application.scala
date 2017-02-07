package controllers

import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, AnyContent, Controller, EssentialAction}

import services.{Authenticated, UserService}

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider, userService: UserService, webJarAssets: WebJarAssets) extends Controller {

  def secured(): EssentialAction = Authenticated.async {
    request =>
      Future.successful(Ok(views.html.index()))
  }

  def securedWithParam(id: Any): EssentialAction = Authenticated.async {
    request =>
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

  def findWebJar(file: String): Action[AnyContent] = {
    val split = file.split("/")
    val path = webJarAssets.locate("rxjs",  split(split.length - 1))
    webJarAssets.at(path)
  }
}
