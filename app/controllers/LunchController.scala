package controllers

import com.google.inject.Inject
import models.Formats._
import models.LunchDto
import play.api.libs.json.Json
import play.api.mvc.Controller
import services.{Authenticated, LunchService}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LunchController @Inject()(lunchService: LunchService) extends Controller {

  def getLunchById(id: String) = Authenticated.async { request =>
    lunchService.getLunchById(id.toInt).map(lunch =>
      Ok(Json.toJson(lunch))
    )
  }

  def createLunch() = Authenticated.async(parse.json) { request =>
    val lunchDto = request.body.as[LunchDto]
    lunchService.createLunch(lunchDto, request.username).map(lunch =>
      Ok(Json.toJson(lunch))
    )
  }
}
