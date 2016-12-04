package services

import java.util.Base64

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.Configuration
import play.api.libs.json.Json

import client.RESTClientWrapper
import com.google.inject.Inject
import models.GoogleModels._

class GoogleAuthorizationService @Inject()(configuration: Configuration, googleAuthenticationClient: RESTClientWrapper, userService: UserService) {

  private val googleTokenUrl = "https://www.googleapis.com/oauth2/v4/token"
  private val authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
  private val lunchMatorClientId = "28348881946-hmdkk177nv7sks841ef4pb7sivacfn5r.apps.googleusercontent.com"
  private val tokenRedirect = "http://localhost:9000/google-token"
  private val grantType = "authorization_code"
  private val clientSecret = "E4TXLJsP-jCkgI1a2xRR9lrm"

  def googleSignInPage(originPage: String = "/welcome"): String = {
    authorizationUrl +
      "response_type=code&" +
      s"client_id=$lunchMatorClientId&" +
      s"redirect_uri=$tokenRedirect&" +
      "scope=profile&" +
      "access_type=online&" +
      "include_granted_scopes=true&" +
      "state=" + originPage
  }

  def getGoogleUserData(authorizationCode: String): Future[GoogleUserInformation] = {
    val formData = Map(
      "code" -> Seq(authorizationCode),
      "client_id" -> Seq(lunchMatorClientId),
      "client_secret" -> Seq(clientSecret),
      "grant_type" -> Seq(grantType),
      "redirect_uri" -> Seq(tokenRedirect)
    )
    googleAuthenticationClient.makePost[GoogleAuthorization](googleTokenUrl, formData).map(decodeUserData)
  }

  private def decodeUserData(googleAuthorization: GoogleAuthorization) = {
    val tokens = googleAuthorization.id_token.split("\\.")
    Json.parse(Base64.getDecoder.decode(tokens(1))).as[GoogleUserInformation]
  }
}
