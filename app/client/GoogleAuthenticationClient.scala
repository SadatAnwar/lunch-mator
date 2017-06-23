package client

import scala.concurrent.{ExecutionContext, Future}

import play.Configuration
import play.api.Logger
import play.mvc.Http

import com.google.inject.Inject
import exceptions.GoogleAuthenticationException
import models.GoogleModels.GoogleAuthorization

class GoogleAuthenticationClient @Inject()(configuration: Configuration, clientWrapper: RestClientWrapper)(implicit ec: ExecutionContext)
{
  private val googleTokenUrl = configuration.getString("google.token.url")
  private val authorizationUrl = configuration.getString("google.auth.url")
  private val lunchMatorClientId = configuration.getString("google.lunchmator.clientid")
  private val tokenRedirect = configuration.getString("google.callback.token")
  private val grantType = "authorization_code"
  private val clientSecret = configuration.getString("google.lunchmator.clientsecret")

  def getGoogleSignInPage(origin: String): String =
  {
    authorizationUrl +
      "response_type=code&" +
      s"client_id=$lunchMatorClientId&" +
      s"redirect_uri=$tokenRedirect&" +
      "scope=profile+email+https://www.googleapis.com/auth/calendar&" +
      "access_type=online&" +
      "include_granted_scopes=true&" +
      s"state=$origin"
  }

  def getGoogleAuthorization(authorizationCode: String): Future[GoogleAuthorization] =
  {
    val headers = List(
      Http.HeaderNames.CONTENT_TYPE -> Http.MimeTypes.FORM,
      Http.HeaderNames.ACCEPT_CHARSET -> "UTF-8"
    )

    val formData = Map(
      "code" -> Seq(authorizationCode),
      "client_id" -> Seq(lunchMatorClientId),
      "client_secret" -> Seq(clientSecret),
      "grant_type" -> Seq(grantType),
      "redirect_uri" -> Seq(tokenRedirect)
    )
    Logger.info(s"Requesting for GoogleAuthorization | formData:[${formData.mkString(",")}]")

    clientWrapper.post[Map[String, Seq[String]], GoogleAuthorization](googleTokenUrl, headers, formData).map {
      case Some(result) => result
      case None => throw new GoogleAuthenticationException
    }
  }
}
