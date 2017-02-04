package client

import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global

import play.api.Logger
import play.api.http.Writeable
import play.api.libs.json.Json
import play.api.libs.ws._

import exceptions.HttpClientInternalException

class RESTClientWrapper @Inject()(ws: WSClient) {

  import scala.concurrent.Future

  import play.api.libs.json.Reads

  def post[T, R](url: String, headers: List[(String, String)], postData: T)(implicit fjs: Reads[R], wrt: Writeable[T]): Future[R] = {
    Logger.debug(s"POST | url:[$url] | headers:[$headers]")
    ws
      .url(url)
      .withHeaders(headers: _*)
      .post(postData)
      .map(response => mapResponse[R](response))
  }

  def get[A](url: String, headers: List[(String, String)] = List(), queryParams: List[(String, String)] = List())(implicit fjs: Reads[A]): Future[A] = {
    ws
      .url(url)
      .withHeaders(headers: _*)
      .withQueryString(queryParams: _*)
      .get()
      .map(response => mapResponse(response))
  }

  private def mapResponse[A](response: WSResponse)(implicit fjs: Reads[A]): A = {
    if (!(200 to 299).contains(response.status)) {
      Logger.error(s"ERROR: ResponseCode:[${response.status}] | ResponseBody:[${response.body}]")
      throw HttpClientInternalException(response.body, response.status)
    }
    Json.parse(response.body).as[A]
  }
}
