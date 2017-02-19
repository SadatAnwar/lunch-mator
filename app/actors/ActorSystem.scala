package actors

import scala.concurrent.ExecutionContext

import play.api.Logger

import actors.messages.{LunchReminderMessage, NewLunchCreatedMessage, ParticipantLeftMessage}
import akka.actor._
import com.google.inject.{Inject, Singleton}
import exceptions.ParticipantService
import services.LunchReminderService

@Singleton
class ActorSystem @Inject()(lunchReminderService: LunchReminderService, participantService: ParticipantService)(implicit ec: ExecutionContext) extends Actor
{
  def receive: PartialFunction[Any, Unit] =
  {
    case NewLunchCreatedMessage(lunchId, user) => participantService.addUserToLunch(user, lunchId)
    case LunchReminderMessage(lunchId) => lunchReminderService.sendReminderFroLunch(lunchId)
    case ParticipantLeftMessage(lunchId) => participantService.deactivateEmptyLunch(lunchId)
    case any => Logger.warn(s"[$any] | Unidentified message")
  }
}

object ActorSystem
{
  def props: Props = Props[ActorSystem]
}
