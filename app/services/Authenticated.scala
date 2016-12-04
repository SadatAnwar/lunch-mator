package services

import scala.concurrent.Future

import play.api.mvc.BodyParsers.parse
import play.api.mvc.Results._
import play.api.mvc._

class AuthenticatedRequest[A](val username: String, request: Request[A]) extends WrappedRequest[A](request) {
}

object Authenticated {

  def async[A](bp: BodyParser[A])(block: (AuthenticatedRequest[A]) => Future[Result]): Action[A] = Action.async(bp) {
    request =>
      request.session.get("email").map {
        username =>
          block(new AuthenticatedRequest(username, request))
      } getOrElse {
        Future.successful(Redirect("/login"))
      }
  }

  def async[A](block: (AuthenticatedRequest[AnyContent]) => Future[Result]): EssentialAction = async(parse.anyContent)(block)

  def apply[A](block: (AuthenticatedRequest[AnyContent]) => Result): EssentialAction = apply(parse.anyContent)(block)

  def apply[A](bp: BodyParser[A])(block: (AuthenticatedRequest[A]) => Result): Action[A] = Action(bp) {
    request =>
      request.session.get("email").map {
        username =>
          block(new AuthenticatedRequest(username, request))
      } getOrElse {
        Redirect("/login")
      }
  }
}
