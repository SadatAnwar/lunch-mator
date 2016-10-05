package controllers

import com.google.inject.Inject
import play.api.mvc.Controller
import services.{Authenticated, LunchService}
import models.Formats.lunchFormat
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext.Implicits.global

class LunchController @Inject()(lunchService: LunchService) extends Controller {

  def getLunchById(id: String) = Authenticated.async { request =>
    lunchService.getLunchById(id.toInt).map(lunch =>
      Ok(Json.toJson(lunch))
    )
  }
}
