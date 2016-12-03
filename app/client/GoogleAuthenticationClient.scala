package client

import javax.inject.Inject

import play.api.libs.ws._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GoogleAuthenticationClient @Inject()(ws: WSClient) {

  private val authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth"

  private val lunchMatorClientId = "28348881946-hmdkk177nv7sks841ef4pb7sivacfn5r.apps.googleusercontent.com"

  private val redirectUri = "http://localhost:9000/google-login"

  private val responseType = "code"

  private val scope = "https://www.googleapis.com/auth/userinfo.profile"


  def authorize(): Future[Unit] = {
    ws
      .url(authorizationUrl)
      .withQueryString("client_id" -> lunchMatorClientId,
        "redirect_uri" -> redirectUri,
        "response_type" -> responseType,
        "scope" -> scope
      )
      .get()
      .map { wsResponse =>
        if (!(200 to 299).contains(wsResponse.status)) {
          sys.error(s"Received unexpected status ${wsResponse.status} : ${wsResponse.body}")
        }

        println(s"OK, received ${wsResponse.body}")

        println(s"The response header Content-Length was ${wsResponse.header("Content-Length")}")
      }
  }
}

