package services

import java.util.Base64

import scala.concurrent.{ExecutionContext, Future}

import play.Configuration
import play.api.Logger
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.Json

import client.GoogleAuthenticationClient
import com.google.inject.Inject
import exceptions.GoogleAuthenticationException
import mappers.UserMapper
import models.GoogleModels._
import persistence.repository.OAuthUser

class GoogleAuthorizationService @Inject()(configuration: Configuration, googleAuthenticationClient: GoogleAuthenticationClient, userService: UserService)(implicit val dbConfigDataProvider: DatabaseConfigProvider, ec: ExecutionContext) extends Service
{

  def getGoogleSignInPage(origin: String = "/welcome"): String =
  {
    googleAuthenticationClient.getGoogleSignInPage(origin)
  }

  def getGoogleAuthorization(authorizationCode: String): Future[GoogleAuthorization] =
  {
    googleAuthenticationClient.getGoogleAuthorization(authorizationCode)
  }

  def googleAuthorize(params: Map[String, String]): Future[GoogleUserInformation] =
  {
    val authorizationCode = params.getOrElse("code", throw new GoogleAuthenticationException)
    val googleUserData = getGoogleAuthorization(authorizationCode)
    googleUserData.map { googleAuthorization =>
      val googleUserInformation = decodeUserData(googleAuthorization)
      saveNewUser(googleUserInformation).map { insertCount =>
        if (insertCount != 0) {
          saveOAuth(googleUserInformation, googleAuthorization)
        }
      }
      googleUserInformation
    }
  }

  private def decodeUserData(googleAuthorization: GoogleAuthorization) =
  {
    val tokens = googleAuthorization.id_token.split("\\.")
    Logger.debug(s"Google response: [${googleAuthorization.toString}]")
    Logger.debug(s"Decoding: [${tokens(1)}]")
    Json.parse(Base64.getDecoder.decode(tokens(1).getBytes("UTF-8"))).as[GoogleUserInformation]
  }

  private def saveOAuth(googleUserInformation: GoogleUserInformation, googleAuthorization: GoogleAuthorization) = usingDB {
    OAuthUser.addNewUser(UserMapper.map(googleUserInformation, googleAuthorization))
  }

  private def saveNewUser(googleUserInformation: GoogleUserInformation) =
  {
    userService.addUser(UserMapper.map(googleUserInformation))
  }
}
