package controllers

import java.util.UUID
import com.google.inject.Inject
import exceptions.ParticipantService
import mappers.LunchMapper
import models.Formats._
import models._
import play.api.libs.json.Json
import play.api.mvc.Controller
import services.{Authenticated, LunchService}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LunchController @Inject()(lunchService: LunchService, participantService: ParticipantService) extends Controller {

  val a = "BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:-//hacksw/handcal//NONSGML v1.0//EN\nBEGIN:VEVENT\nUID:" + UUID.randomUUID() + "@yourhost.test\nDTSTAMP:\" . gmdate('Ymd').'T'. gmdate('His') . \"Z\nDTSTART:19970714T170000Z\nDTEND:19970715T035959Z\nSUMMARY:Bastille Day Party\nEND:VEVENT\nEND:VCALENDAR;"

  def createLunch() = Authenticated.async(parse.json) { request =>
    val lunchDto = request.body.as[CreateLunchDto]
    lunchService.createLunch(request.username, lunchDto).map(lunch =>
      Created
    )
  }

  def joinLunch(lunchId: Int) = Authenticated.async { request =>
    participantService.addUserToLunch(request.username, lunchId).map(participant =>
      Ok(Json.toJson(participant))
    )
  }

  def getLunch = Authenticated.async { request =>
    lunchService.getAllLunchNotPast.map { lunchRestSeq =>
      val lunchSeq = lunchRestSeq.map(a => LunchMapper.map(a._1, a._2, a._3))
      Ok(Json.toJson(lunchSeq))
    }
  }

  def getLunchDetail(lunchId: Int) = Authenticated.async { request =>
    val eventualDtoes: Future[Seq[ParticipantDto]] = participantService.getParticipants(lunchId)
    val detail = lunchService.getLunchDetail(lunchId)
    detail.map(a => Ok(LunchMapper.map(a._1, a._2, eventualDtoes)))
  }
}
