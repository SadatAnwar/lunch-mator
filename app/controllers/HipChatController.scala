package controllers

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller, EssentialAction}

import com.google.inject.Inject
import models.Formats._
import models.{HipChatMessageDto, InvitationDto}
import services.{Authenticated, HipChatService}

class HipChatController @Inject()(hipChatService: HipChatService) extends Controller {

  def searchUsers(name: String): EssentialAction = Authenticated.async { request =>

    hipChatService.getUsersWithNameIn(name).map { result =>

      Ok(Json.toJson(result))
    }
  }

  def sendMessage: Action[JsValue] = Authenticated.async(parse.json) { request =>
    val messageDto = request.body.as[HipChatMessageDto]

    hipChatService.sendMessage(messageDto).map(_ => NoContent)
  }

  def invite(): Action[JsValue] = Action.async(parse.json) { request =>
    val invitation = request.body.as[InvitationDto]

    hipChatService.sendInvitation(invitation).map(_ => NoContent)
  }
}
