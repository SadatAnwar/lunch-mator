package controllers

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.json.Json
import play.api.mvc.{Controller, EssentialAction}

import com.google.inject.Inject
import exceptions.ParticipantService
import mappers.ParticipantMapper
import models.Formats._
import services.Authenticated

class ParticipantController @Inject()(participantService: ParticipantService) extends Controller
{

  def getParticipants(lunchId: Int): EssentialAction = Authenticated.async { request =>
    participantService.getParticipationDetails(lunchId).map { participants =>
      val participant = ParticipantMapper.map(participants)
      Ok(Json.toJson(participant))
    }
  }
}
