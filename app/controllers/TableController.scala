package controllers

import com.google.inject.Inject
import play.api.mvc._
import services.CreateTableService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TableController @Inject()(createTableService: CreateTableService) extends Controller {

  def createTable(name: String, restaurant: String) = Action.async(parse.json) {
    implicit request =>
      //createTableService.createNewTableAt(name, restaurant).map(response => autoResult(response))
      Future(Ok(""))
  }
}
