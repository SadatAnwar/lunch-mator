package controllers

import scala.concurrent.ExecutionContext

import play.api.mvc.{Action, AnyContent, Controller}

import actors.LunchReminderActor.LunchReminderMessage
import com.google.inject.Inject
import scheduler.Scheduler
import services.LunchService

class DebugController @Inject()(lunchService: LunchService, scheduler: Scheduler)(implicit ec: ExecutionContext) extends Controller
{
  def rescheduleReminder(lunchId: Int): Action[AnyContent] = Action.async {
    lunchService.getLunchAndRestaurant(lunchId).map { a =>
      scheduler.scheduleMessage(LunchReminderMessage(lunchId), a._1.startTime.minusMinutes(10))
      Ok
    }
  }
}
