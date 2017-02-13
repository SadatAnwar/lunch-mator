package actors

import play.api.libs.concurrent.AkkaGuiceSupport

import com.google.inject.AbstractModule
import scheduler.Scheduler

class Module extends AbstractModule with AkkaGuiceSupport
{
  def configure(): Unit =
  {
    bindActor[LunchReminderActor]("lunch-reminder-actor")
    bind(classOf[Scheduler]).asEagerSingleton()
  }
}
