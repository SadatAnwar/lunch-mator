package controllers

import com.google.inject.Inject
import exceptions.ParticipantService
import models.Formats._
import models.LunchDto
import play.api.libs.json.Json
import play.api.mvc.Controller
import services.{Authenticated, LunchService}
import scala.concurrent.ExecutionContext.Implicits.global

class LunchController @Inject()(lunchService: LunchService, participantService: ParticipantService) extends Controller {

  def getLunchById(id: String) = Authenticated.async { request =>
    lunchService.getLunchById(id.toInt).map(lunch =>
      Ok(Json.toJson(lunch))
    )
  }

  def createLunch() = Authenticated.async(parse.json) { request =>
    val lunchDto = request.body.as[LunchDto]
    lunchService.createLunch(request.username, lunchDto).map(lunch =>
      Created
    )
  }

  def joinLunch(lunchId: String) = Authenticated.async { request =>
    participantService.addUserToLunch(request.username, 1).map(participant =>
      Ok
    )
  }
}
