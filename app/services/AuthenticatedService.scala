package services

import scala.concurrent.{ExecutionContext, Future}

import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.BodyParsers.parse
import play.api.mvc._

import com.google.inject.Inject
import exceptions.AuthenticationException
import models.UserRow
import persistence.repository.Users

class AuthenticatedService @Inject()(implicit db: DatabaseConfigProvider, ec: ExecutionContext) extends Service
{

  type ControllerBlock[A] = (AuthenticatedRequest[A]) => Future[Result]

  def async[A](block: ControllerBlock[AnyContent]): EssentialAction = async(parse.anyContent)(block)

  def async[A](bp: BodyParser[A])(block: ControllerBlock[A]): Action[A] = Action.async(bp) {
    request =>
      validateSession(request).flatMap { validUser =>
        if (validUser.isDefined) {
          block(new AuthenticatedRequest(request.session.get("email").get, validUser.get, request))
        }
        else {
          throw new AuthenticationException(origin = request.path)
        }
      }
  }

  def validateSession(request: Request[Any]): Future[Option[UserRow]] =
  {
    if (request.session.get("email").isEmpty) {
      return Future.successful(None)
    }

    validateUser(request.session.get("email").get)
  }

  private def validateUser(email: String): Future[Option[UserRow]] = usingDB {
    Users.getByEmail(email)
  }
}

case class AuthenticatedRequest[A](username: String, userRow: UserRow, request: Request[A]) extends WrappedRequest[A](request)
