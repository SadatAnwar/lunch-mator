package controllers

import scala.concurrent.ExecutionContext

import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller, EssentialAction}

import actors.messages.Message.NewCommentMessage
import com.google.inject.Inject
import models.Formats._
import models.NewComment
import services.{AuthenticatedService, ChatMessageService, MessageService}

class CommentController @Inject()(chatMessageService: ChatMessageService, messageService: MessageService)(implicit ec: ExecutionContext, db: DatabaseConfigProvider) extends AuthenticatedService with Controller
{
  def getComments(lunchId: Int): EssentialAction = async { request =>
    chatMessageService.getCommentsForLunch(lunchId).map { messages =>
      Ok(Json.toJson(messages))
    }
  }

  def postComment(lunchId: Int): Action[JsValue] = async(parse.json) { request =>
    implicit val user = request.userRow
    val message = request.body.as[NewComment]
    chatMessageService.postComment(message.text, lunchId).map { _ =>
      messageService.publishMessage(NewCommentMessage(lunchId, s"${user.firstName} ${user.lastName}", message.text))
      Ok
    }
  }
}
