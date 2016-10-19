package controllers

import com.google.inject.Inject
import exceptions.ParticipantService
import mappers.LunchTableMapper
import models.Formats._
import models.{CreateLunchDto, Lunch}
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
    val lunchDto = request.body.as[CreateLunchDto]
    lunchService.createLunch(request.username, lunchDto).map(lunch =>
      Created
    )
  }

  def joinLunch(lunchId: Int) = Authenticated.async(parse.json) { request =>
    participantService.addUserToLunch(request.username, lunchId).map(participant =>
      Ok
    )
  }

  def getLunch() = Authenticated.async { reqiest =>
    lunchService.getAllLunchTables.map { lunchRestSeq =>
      val lunchSeq = lunchRestSeq.map { (a) =>
        Lunch(a._1.id.getOrElse(-1), a._2, a._1.maxSize, a._1.startTime, a._1.anonymous)
      }
      Ok(Json.toJson(lunchSeq))
    }
  }
}
