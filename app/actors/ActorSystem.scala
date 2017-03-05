package actors

import scala.concurrent.ExecutionContext

import play.api.Logger

import actors.messages.Message.{LunchReminderMessage, NewCommentMessage, NewLunchCreatedMessage, ParticipantLeftMessage}
import akka.actor._
import com.google.inject.{Inject, Singleton}
import exceptions.ParticipantService
import services.NotificationService

@Singleton
class ActorSystem @Inject()(notificationService: NotificationService, participantService: ParticipantService)(implicit ec: ExecutionContext) extends Actor
{
  def receive: PartialFunction[Any, Unit] =
  {
    case NewLunchCreatedMessage(lunchId, user) => participantService.addUserToLunch(user, lunchId)
    case LunchReminderMessage(lunchId) => notificationService.sendReminderForLunch(lunchId)
    case ParticipantLeftMessage(lunchId) => participantService.deactivateEmptyLunch(lunchId)
    case NewCommentMessage(lunchId, author, comment) => notificationService.sendNewMessageNotification(lunchId, author, comment)
    case any => Logger.warn(s"[$any] | Unidentified message")
  }
}

object ActorSystem
{
  def props: Props = Props[ActorSystem]
}
