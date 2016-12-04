package services

import java.util.Base64

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.Configuration
import play.api.libs.json.Json

import client.RESTClientWrapper
import com.google.inject.Inject
import exceptions.GoogleAuthenticationException
import mappers.UserMapper
import models.GoogleModels._
import persistence.repository.{OAuthUser, Users}

class GoogleAuthorizationService @Inject()(configuration: Configuration, googleAuthenticationClient: RESTClientWrapper, userService: UserService) {

  private val googleTokenUrl = "https://www.googleapis.com/oauth2/v4/token"
  private val authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth?"
  private val lunchMatorClientId = "28348881946-hmdkk177nv7sks841ef4pb7sivacfn5r.apps.googleusercontent.com"
  private val tokenRedirect = "http://localhost:9000/google-token"
  private val grantType = "authorization_code"
  private val clientSecret = "E4TXLJsP-jCkgI1a2xRR9lrm"

  def getGoogleSignInPage(originPage: String = "/welcome"): String = {
    authorizationUrl +
      "response_type=code&" +
      s"client_id=$lunchMatorClientId&" +
      s"redirect_uri=$tokenRedirect&" +
      "scope=profile&" +
      "access_type=online&" +
      "include_granted_scopes=true&" +
      s"state=$originPage"
  }

  def googleAuthorize(params: Map[String, String]): Future[String] = {
    val authorizationCode = params.getOrElse("code", throw new GoogleAuthenticationException)
    val startPage = params.getOrElse("state", "/welcome")
    val googleUserData = getGoogleAuthorization(authorizationCode)
    googleUserData.flatMap {
      googleAuthorization =>
        val googleUserInformation = decodeUserData(googleAuthorization)
        saveOAuth(googleUserInformation, googleAuthorization).map { insertCount =>
          if (insertCount != 0) {
            saveNewUser(googleUserInformation)
          }
        }.map(_ =>
          startPage
        )
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
    Json.parse(Base64.getDecoder.decode(tokens(1))).as[GoogleUserInformation]
  }

  private def saveOAuth(googleUserInformation: GoogleUserInformation, googleAuthorization: GoogleAuthorization) = usingDB {
    OAuthUser.addNewUser(UserMapper.map(googleUserInformation, googleAuthorization))
  }

  private def saveNewUser(googleUserInformation: GoogleUserInformation) = usingDB {
    Users.addNewUser(UserMapper.map(googleUserInformation))
  }
}
