package services

import java.util.Base64

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.Configuration
import play.api.Logger
import play.api.libs.json.Json

import client.RESTClientWrapper
import com.google.inject.Inject
import exceptions.GoogleAuthenticationException
import mappers.UserMapper
import models.GoogleModels._
import persistence.repository.OAuthUser

class GoogleAuthorizationService @Inject()(configuration: Configuration, googleAuthenticationClient: RESTClientWrapper, userService: UserService) {

  private val googleTokenUrl = "https://www.googleapis.com/oauth2/v4/token"
  private val authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
  private val lunchMatorClientId = configuration.underlying.getString("google.lunchmator.clientid")
  private val tokenRedirect = configuration.underlying.getString("google.callback.token")
  private val grantType = "authorization_code"
  private val clientSecret = configuration.underlying.getString("google.lunchmator.clientsecret")

  def getGoogleSignInPage(origin: String = "/welcome"): String = {
    authorizationUrl +
      "response_type=code&" +
      s"client_id=$lunchMatorClientId&" +
      s"redirect_uri=$tokenRedirect&" +
      "scope=profile&" +
      "access_type=online&" +
      "include_granted_scopes=true&" +
      s"state=$origin"
  }

  def googleAuthorize(params: Map[String, String]): Future[GoogleUserInformation] = {
    val authorizationCode = params.getOrElse("code", throw new GoogleAuthenticationException)
    val googleUserData = getGoogleAuthorization(authorizationCode)
    googleUserData.map {
      googleAuthorization =>
        val googleUserInformation = decodeUserData(googleAuthorization)
        saveNewUser(googleUserInformation).map { insertCount =>
          if (insertCount != 0) {
            saveOAuth(googleUserInformation, googleAuthorization)
          }
        }
        googleUserInformation
    }
  }

  def getGoogleAuthorization(authorizationCode: String): Future[GoogleAuthorization] = {
    val formData = Map(
      "code" -> Seq(authorizationCode),
      "client_id" -> Seq(lunchMatorClientId),
      "client_secret" -> Seq(clientSecret),
      "grant_type" -> Seq(grantType),
      "redirect_uri" -> Seq(tokenRedirect)
    )
    googleAuthenticationClient.makePost[GoogleAuthorization](googleTokenUrl, formData)
  }

  private def decodeUserData(googleAuthorization: GoogleAuthorization) = {
    val tokens = googleAuthorization.id_token.split("\\.")
    Logger.info(s"Received response from google ${googleAuthorization.toString}")
    Logger.info(s"Decoding ${tokens(1)}")
    Json.parse(Base64.getDecoder.decode(tokens(1).getBytes("UTF-8"))).as[GoogleUserInformation]
  }

  private def saveOAuth(googleUserInformation: GoogleUserInformation, googleAuthorization: GoogleAuthorization) = usingDB {
    OAuthUser.addNewUser(UserMapper.map(googleUserInformation, googleAuthorization))
  }

  private def saveNewUser(googleUserInformation: GoogleUserInformation) = {
    userService.addUser(UserMapper.map(googleUserInformation))
  }
}
