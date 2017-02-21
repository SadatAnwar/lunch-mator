package controllers

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller, EssentialAction}

import com.google.inject.Inject
import exceptions.ParticipantService
import mappers.LunchMapper
import models.Formats._
import models._
import services.{Authenticated, LunchService}

class LunchController @Inject()(lunchService: LunchService, participantService: ParticipantService) extends Controller
{

  def createLunch(): Action[JsValue] = Authenticated.async(parse.json) { request =>
    val lunchDto = request.body.as[CreateLunchDto]
    lunchService.createLunch(request.userRow, lunchDto).map(lunchId =>
      Created(Json.toJson(lunchId))
    )
  }

  def joinLunch(lunchId: Int): EssentialAction = Authenticated.async { request =>
    participantService.addUserToLunch(request.userRow, lunchId).map(participant =>
      if (participant == 1) {
        Ok(Json.toJson(participant))
      } else {
        Conflict("Unable to join as lunch is inactive.")
      }
    )
  }

  def leaveLunch: Action[JsValue] = Authenticated.async(parse.json) { request =>
    val lunchDto = request.body.as[MyLunchDto]
    participantService.removeUserFromLunch(request.userRow, lunchDto.id).map(rowsUpdated =>
      Ok(Json.toJson("Success"))
    )
  }

  def getAllAvailableLunch: EssentialAction = Authenticated.async { request =>
    lunchService.getAllLunchNotPast(request.username).map { lunchRestSeq =>
      val lunchSeq = lunchRestSeq.map(a => LunchMapper.map(a._1, a._2, a._3, a._4))
      Ok(Json.toJson(lunchSeq))
    }
  }

  def getMyLunch: EssentialAction = Authenticated.async { request =>
    lunchService.getLunchForUserNotPast(request.username).map { lunchRestSeq =>
      val lunchSeq = lunchRestSeq.map(a => LunchMapper.map(a._1, a._2))
      Ok(Json.toJson(lunchSeq))
    }
  }

  def getLunch(lunchId: Int): EssentialAction = Authenticated.async { request =>
    lunchService.getLunchDetail(request.username, lunchId)
      .map { lunchRestSeq =>
        val lunchSeq = lunchRestSeq.map(a => LunchMapper.map(a._1, a._2, a._3, a._4))
        if (lunchSeq.size == 1) {
          Ok(Json.toJson(lunchSeq(0)))
        } else {
          NotFound(s"Could not find any lunch with Id: [$lunchId]")
        }
      }
  }
}
