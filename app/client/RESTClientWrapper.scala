package client

import javax.inject.Inject
import exceptions.GoogleAuthenticationException
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws._
import play.mvc.Http
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RESTClientWrapper @Inject()(ws: WSClient) {

  def makePost(url: String, postData: Map[String, Seq[String]]): Future[JsValue] = {
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
        Json.parse(wsResponse.body)
      }
  }
}

