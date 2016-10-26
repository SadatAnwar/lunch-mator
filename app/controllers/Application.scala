package controllers

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, Controller}
import services.Authenticated


class Application @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {

  def secured() = Authenticated {
    request =>
      Ok(views.html.index())
  }

  def securedWithParam(id: Any) = Authenticated {
    request =>
      Ok(views.html.index())
  }

  def unSecure() =  Action {
    Ok(views.html.index())
  }
}
