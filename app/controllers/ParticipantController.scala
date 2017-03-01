package controllers

import scala.concurrent.ExecutionContext

import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json
import play.api.mvc.{Controller, EssentialAction}

import com.google.inject.Inject
import exceptions.ParticipantService
import mappers.ParticipantMapper
import models.Formats._
import services.AuthenticatedService

class ParticipantController @Inject()(participantService: ParticipantService)(implicit db: DatabaseConfigProvider, ec: ExecutionContext) extends AuthenticatedService with Controller
{

  def getParticipants(lunchId: Int): EssentialAction = async { request =>
    participantService.getParticipationDetails(lunchId).map { participants =>
      val participant = ParticipantMapper.map(participants)
      Ok(Json.toJson(participant))
    }
  }
}
