package actors

import scala.concurrent.ExecutionContext

import actors.LunchReminderActor.LunchReminderMessage
import akka.actor._
import com.google.inject.{Inject, Singleton}
import services.LunchReminderService

@Singleton
class LunchReminderActor @Inject()(lunchReminderService: LunchReminderService)(implicit executionContext: ExecutionContext) extends Actor
{
  def receive: PartialFunction[Any, Unit] =
  {
    case LunchReminderMessage(lunchId) => lunchReminderService.sendReminderFroLunch(lunchId)
  }
}

object LunchReminderActor
{
  def props: Props = Props[LunchReminderActor]

  case class LunchReminderMessage(lunchId: Int)

}
