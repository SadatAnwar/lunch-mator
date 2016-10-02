package services

import play.api.Play
import play.api.mvc.Session
import scala.concurrent.duration.{Duration, _}
import scala.concurrent.{Await, Future}

object SessionService {

  lazy val username: String = Play.maybeApplication.flatMap(_.configuration.getString("session.username")) getOrElse "usname"

  def getUserId(createUser: => Future[Int], getUserId: String => Future[Int])(implicit session: Session): Int = {
    session.get("userid") match {
      case Some(x) => x.toInt
      case None => {
        session.get("email") match {
          case None => Await.result(createUser, Duration(20, SECONDS))
          case Some(userName) => Await.result(getUserId(userName), Duration(20, SECONDS))
        }
      }
    }
  }
}
