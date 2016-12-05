package controllers

import javax.inject.Inject

import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, Controller, EssentialAction}

import services.Authenticated

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {

  def secured(): EssentialAction = Authenticated.async {
    request =>
      Future.successful(Ok(views.html.index()))
  }

  def securedWithParam(id: Any): EssentialAction = Authenticated.async {
    request =>
      Future.successful(Ok(views.html.index()))
  }

  def unSecure() = Action { request =>

    Ok(views.html.index())
  }
}
