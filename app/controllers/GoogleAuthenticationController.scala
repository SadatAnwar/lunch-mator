package controllers

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.mvc.{Action, AnyContent, Controller}

import com.google.inject.Inject
import services.GoogleAuthorizationService

class GoogleAuthenticationController @Inject()(googleAuthorizationService: GoogleAuthorizationService) extends Controller {

  def redirectToGoogle() = Action {
    Redirect(googleAuthorizationService.getGoogleSignInPage())
  }

  def authorize(): Action[AnyContent] = Action.async { request =>
    val params = request.queryString.map { case (k, v) => k -> v.mkString }
    val returnPage = googleAuthorizationService.googleAuthorize(params)
    returnPage.map { address =>
      Redirect(address)
    }
  }
}
