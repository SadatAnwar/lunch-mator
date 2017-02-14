package scheduler

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

import play.api.Logger

import akka.actor.{ActorRef, ActorSystem}
import com.google.inject.Inject
import com.google.inject.name.Named
import org.joda.time.{DateTime, Seconds}

class Scheduler @Inject()(system: ActorSystem, @Named("lunch-reminder-actor") lunchReminderActor: ActorRef)(implicit ec: ExecutionContext)
{

  def scheduleMessage(message: Any, deliveryTime: DateTime): Unit =
  {
    val now = DateTime.now()
    val delayInterval = Seconds.secondsBetween(now, deliveryTime).getSeconds

    if (delayInterval > 0) {
      Logger.info(s"Message scheduled in $delayInterval seconds")
      system.scheduler.scheduleOnce(delayInterval.seconds, lunchReminderActor, message)
    }
  }
}
