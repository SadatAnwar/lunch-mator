package client

import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.libs.json.Json
import play.api.libs.ws._
import play.mvc.Http

import exceptions.GoogleAuthenticationException

class RESTClientWrapper @Inject()(ws: WSClient) {

  import scala.concurrent.Future

  import play.api.libs.json.Reads

  def makePost[A](url: String, postData: Map[String, Seq[String]])(implicit fjs: Reads[A]): Future[A] = {
    ws
      .url(url)
      .withHeaders(
        Http.HeaderNames.CONTENT_TYPE -> "application/x-www-form-urlencoded",
        "charset" -> "utf-8")
      .post(postData)
      .map { wsResponse =>
        if (!(200 to 299).contains(wsResponse.status)) {
          throw new GoogleAuthenticationException
        }
        Json.parse(wsResponse.body).as[A]
      }
  }
}

