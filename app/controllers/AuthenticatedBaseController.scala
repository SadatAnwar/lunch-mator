package controllers

import models.UserSession
import persistence.repository.UserIdentities
import play.api.mvc.{Controller, Request, Result}
import services.{UserIdentityHelper, usingDB}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class AuthenticatedBaseController extends Controller {

  def withUser[T](request: Request[Any])(block : => Future[Result]) = {
    val email = request.session.get("email").getOrElse("")
    val sessionUuid = request.session.get("sessionUuid").getOrElse("")
    val userSession = UserSession(email, sessionUuid)
    usingDB(UserIdentities.getUserIdentity(email)).map{
      userIdentities=> UserIdentityHelper.validateUserSession(userSession, userIdentities.head)
    }.flatMap{
      case true => block
      case false => Future(Unauthorized)
    }
  }
}
