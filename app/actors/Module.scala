package actors

import play.api.libs.concurrent.AkkaGuiceSupport

import com.google.inject.AbstractModule
import services.{MessageService, StartupService}

class Module extends AbstractModule with AkkaGuiceSupport
{
  def configure(): Unit =
  {
    bindActor[ActorSystem]("lunch-mator-actor")
    bind(classOf[MessageService]).asEagerSingleton()
    bind(classOf[StartupService]).asEagerSingleton()
  }
}
