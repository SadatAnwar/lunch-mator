package client

import play.api.libs.ws.ning.NingWSClient

import scala.concurrent.Future

object GoogleAuthenticationClient {

  val authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth"

  val lunchMatorClientId = "client_id=28348881946-hmdkk177nv7sks841ef4pb7sivacfn5r.apps.googleusercontent.com"

  val redirectUri = "http://localhost:9000/google-login"

  val responseType = "code"

  val scope = "https://www.googleapis.com/auth/userinfo.profile"

  val wsClient = NingWSClient()

  def authorize(): Future[Unit] = {
    wsClient
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
