package services

import play.api.mvc.BodyParsers.parse
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.Future

class AuthenticatedRequest[A](val username: String, request: Request[A]) extends WrappedRequest[A](request) {
}

object Authenticated {

  def async[A](bp: BodyParser[A])(block: (AuthenticatedRequest[A]) => Future[Result]) = Action.async(bp) {
    request =>
      request.session.get("email").map {
        username =>
          block(new AuthenticatedRequest(username, request))
      } getOrElse {
        Future.successful(Forbidden)
      }
  }

  def async[A](block: (AuthenticatedRequest[AnyContent]) => Future[Result]): EssentialAction = async(parse.anyContent)(block)

  def apply[A](block: (AuthenticatedRequest[AnyContent]) => Result): EssentialAction = apply(parse.anyContent)(block)

  def apply[A](bp: BodyParser[A])(block: (AuthenticatedRequest[A]) => Result) = Action(bp) {
    request =>
      request.session.get("email").map {
        username =>
          block(new AuthenticatedRequest(username, request))
      } getOrElse {
        Redirect("/login")
      }
  }
}
