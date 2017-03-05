package controllers

import scala.concurrent.ExecutionContext

import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller, EssentialAction}

import com.google.inject.Inject
import models.Formats._
import models.{HipChatCommunication, HipChatMessageDto}
import services.{AuthenticatedService, HipChatService}

class HipChatController @Inject()(hipChatService: HipChatService)(implicit db: DatabaseConfigProvider, ec: ExecutionContext) extends AuthenticatedService with Controller
{

  def searchUsers(name: String): EssentialAction = async(parse.tolerantText) { request =>

    hipChatService.getUsersWithNameIn(name).map { result =>

      Ok(Json.toJson(result))
    }
  }

  def sendMessage: Action[JsValue] = async(parse.json) { request =>
    val messageDto = request.body.as[HipChatMessageDto]

    hipChatService.sendMessage(messageDto).map(_ => NoContent)
  }

  def invite(): Action[JsValue] = async(parse.json) { request =>
    val invitation = request.body.as[HipChatCommunication]
    implicit val userName = request.userRow.email

    hipChatService.sendInvitation(invitation).map(_ => NoContent)
  }
}
