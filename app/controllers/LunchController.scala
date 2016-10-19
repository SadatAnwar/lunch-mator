package controllers

import java.util.UUID

import com.google.inject.Inject
import exceptions.ParticipantService
import models.Formats._
import models.{CreateLunchDto, Lunch}
import play.api.libs.json.Json
import play.api.mvc.Controller
import services.{Authenticated, LunchService}

import scala.concurrent.ExecutionContext.Implicits.global

class LunchController @Inject()(lunchService: LunchService, participantService: ParticipantService) extends Controller {

  def getLunchById(id: String) = Authenticated.async { request =>
    val a = "BEGIN:VCALENDAR\nVERSION:2.0\nPRODID:-//hacksw/handcal//NONSGML v1.0//EN\nBEGIN:VEVENT\nUID:"+ UUID.randomUUID() + "@yourhost.test\nDTSTAMP:\" . gmdate('Ymd').'T'. gmdate('His') . \"Z\nDTSTART:19970714T170000Z\nDTEND:19970715T035959Z\nSUMMARY:Bastille Day Party\nEND:VEVENT\nEND:VCALENDAR;"
    lunchService.getLunchById(id.toInt).map(lunch =>
      Ok(a).withHeaders("Content-type" -> "text/calendar", "Content-Disposition"->"inline" )
    )
  }


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

  def getLunch() = Authenticated.async { reqiest =>
    lunchService.getAllLunchTables.map { lunchRestSeq =>
      val lunchSeq = lunchRestSeq.map { (a) =>
        Lunch(a._1.id.getOrElse(-1), a._2, a._1.maxSize, a._1.startTime, a._1.anonymous)
      }
      Ok(Json.toJson(lunchSeq))
    }
  }
}
