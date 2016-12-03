package services

import client.GoogleAuthenticationClient
import com.google.inject.Inject
import play.Configuration

class GoogleAuthorizationService @Inject()(configuration: Configuration, googleAuthenticationClient: GoogleAuthenticationClient) {

  private val authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth"

  private val lunchMatorClientId = "28348881946-hmdkk177nv7sks841ef4pb7sivacfn5r.apps.googleusercontent.com"

  private val redirectUri = "http://localhost:9000/google-login"

  private val responseType = "code"

  private val scope = "https://www.googleapis.com/auth/userinfo.profile"

  def googleSignInPage(originPage: String = "/welcome") = {
    "https://accounts.google.com/o/oauth2/v2/auth?" +
      "response_type=code&" +
      "client_id=28348881946-hmdkk177nv7sks841ef4pb7sivacfn5r.apps.googleusercontent.com&" +
      "redirect_uri=http://localhost:9000/google-token&" +
      "scope=profile&" +
      "access_type=online&" +
      "include_granted_scopes=true&" +
      "state=" + originPage
  }
}
