package scheduler

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

import play.api.Logger

import akka.actor.{ActorRef, ActorSystem}
import com.google.inject.Inject
import com.google.inject.name.Named
import org.joda.time.DateTime

class Scheduler @Inject()(system: ActorSystem, @Named("lunch-reminder-actor") lunchReminderActor: ActorRef)(implicit ec: ExecutionContext)
{

  def scheduleMessage(message: Any, deliveryTime: DateTime): Unit =
  {
    val now = DateTime.now()
    val delayInterval = now.getSecondOfMinute - deliveryTime.getSecondOfMinute

    if (delayInterval > 0) {
      Logger.debug(s"Message scheduled in $delayInterval seconds")
      system.scheduler.scheduleOnce(delayInterval.seconds, lunchReminderActor, message)
    }
  }
}
