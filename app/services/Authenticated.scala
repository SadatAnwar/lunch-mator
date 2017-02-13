package services

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.mvc.BodyParsers.parse
import play.api.mvc._

import exceptions.AuthenticationException
import persistence.repository.Users

class AuthenticatedRequest[A](val username: String, val request: Request[A]) extends WrappedRequest[A](request)
{
}

object Authenticated
{

  type ControllerBlock[A] = (AuthenticatedRequest[A]) => Future[Result]

  def async[A](block: ControllerBlock[AnyContent]): EssentialAction = async(parse.anyContent)(block)

  def async[A](bp: BodyParser[A])(block: ControllerBlock[A]): Action[A] = Action.async(bp) {
    request =>
      validateSession(request).flatMap { valid =>
        if (valid) {
          block(new AuthenticatedRequest(request.session.get("email").get, request))
        }
        else {
          throw new AuthenticationException(origin = request.path)
        }
      }
  }

  def validateSession(request: Request[Any]): Future[Boolean] =
  {
    if (request.session.get("email").isEmpty) {
      return Future.successful(false)
    }

    validateUser(request.session.get("email").get)
  }

  private def validateUser(email: String): Future[Boolean] = usingDB {
    Users.isPresent(email)
  }
}
