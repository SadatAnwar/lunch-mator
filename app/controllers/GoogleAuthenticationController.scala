package controllers

import com.google.inject.Inject
import play.api.mvc.Controller
import services.AuthenticationService

class GoogleAuthenticationController @Inject()(authenticationService: AuthenticationService) extends Controller {
}
