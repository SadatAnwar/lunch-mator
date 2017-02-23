package controllers

import scala.concurrent.{ExecutionContext, Future}

import play.api.libs.json.JsValue
import play.api.mvc.{Action, Controller, EssentialAction}

import com.google.inject.Inject
import models.ChatMessage
import services.{Authenticated, ChatMessageService}

class ChatMessagesController @Inject()(chatMessageService: ChatMessageService)(implicit ec: ExecutionContext) extends Controller
{
  def getMessages(lunchId: Int): EssentialAction = Authenticated.async { request =>
    chatMessageService.getMessagesForLunch(lunchId).map(Ok(_))
  }

  def postMessage(lunchId: Int): Action[JsValue] = Authenticated.async(parse.json) { request =>
    implicit val user = request.userRow
    val message = request.body.as[ChatMessage]
    chatMessageService.postMessage(message.meesage)
    Future.successful(Ok)
  }
}
