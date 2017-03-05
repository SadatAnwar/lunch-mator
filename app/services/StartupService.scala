package services

import scala.concurrent.ExecutionContext

import play.api.Logger

import actors.messages.Message.LunchReminderMessage
import com.google.inject.{Inject, Singleton}

@Singleton
class StartupService @Inject()(lunchService: LunchService, messageService: MessageService)(implicit ec: ExecutionContext)
{
  def rescheduleRemindersForAllActiveLunch(): Unit =
  {
    lunchService.getAllActiveLunch.map { lunchDetails =>
      lunchDetails.foreach { lunchDetail =>
        Logger.info(s"LunchId:[${lunchDetail.lunch.id.get}] | Schedule reminder")
        messageService.scheduleMessage(LunchReminderMessage(lunchDetail.lunch.id.get), lunchDetail.lunch.startTime.minusMinutes(10))
      }
    }
  }

  rescheduleRemindersForAllActiveLunch()
}
