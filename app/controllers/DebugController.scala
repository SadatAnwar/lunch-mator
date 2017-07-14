package controllers

import scala.concurrent.ExecutionContext

import play.api.mvc.{Action, AnyContent, Controller}

import actors.messages.Message
import com.google.inject.Inject
import services.{LunchService, MessageService}

class DebugController @Inject()(lunchService: LunchService, scheduler: MessageService)(implicit ec: ExecutionContext) extends Controller
{
  def rescheduleReminder(lunchId: Int): Action[AnyContent] = Action.async {
    lunchService.getLunchAndRestaurant(lunchId).map { a =>
      scheduler.scheduleMessage(Message.LunchReminderMessage(lunchId), a._1.startTime.minusMinutes(10))
      Ok
    }
  }
}
