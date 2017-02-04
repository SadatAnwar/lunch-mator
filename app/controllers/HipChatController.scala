package controllers

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}

import com.google.inject.Inject
import models.Formats._
import models.HipChatMessageDto
import services.HipChatService

class HipChatController @Inject()(hipChatService: HipChatService) extends Controller {

  def searchUsers(name: String): Action[AnyContent] = Action.async { request =>

    hipChatService.getUsersWithNameIn(name).map { result =>
      Ok(Json.toJson(result))
    }
  }

  def sendMessage = Action(parse.json) { request =>
    val messageDto = request.body.as[HipChatMessageDto]
    Logger.info(s"$messageDto")
    hipChatService.sendMessage(messageDto.users, messageDto.message)
    Ok
  }
}
