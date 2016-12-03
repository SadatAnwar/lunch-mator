package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import services.GoogleAuthorizationService

class GoogleAuthenticationController @Inject()(googleAuthorizationService: GoogleAuthorizationService) extends Controller {

  def redirectToGoogle() = Action {
    Redirect(googleAuthorizationService.googleSignInPage())
  }

  def authorize() = Action { request =>
    val params = request.queryString.map { case (k, v) => k -> v.mkString }
    val authorizationCode = params.getOrElse("code", throw new RuntimeException)
    val startPage = params.getOrElse("state", "/welcome")
    println(authorizationCode)
    println(startPage)
    googleAuthorizationService.getGoogleUserData(authorizationCode)
    Redirect(startPage)
  }
}
