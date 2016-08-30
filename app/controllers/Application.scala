package controllers

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc._
import slick.driver.JdbcProfile

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {

  val dbConfig = dbConfigProvider.get[JdbcProfile]

  def index(any: String) = Action {
    Ok(views.html.index())
  }
}
