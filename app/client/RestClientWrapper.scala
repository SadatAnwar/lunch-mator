package client

import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.Logger
import play.api.http.Writeable
import play.api.libs.json.{Json, Reads}
import play.api.libs.ws._

import exceptions.HttpClientInternalException

class RestClientWrapper @Inject()(ws: WSClient)
{

  def post[T, R](url: String, headers: List[(String, String)], postData: T)(implicit fjs: Reads[R], wrt: Writeable[T]): Future[Option[R]] =
  {
    Logger.debug(s"POST | url:[$url] | headers:[$headers] | body:[$postData]")
    ws
      .url(url)
      .withHeaders(headers: _*)
      .post(postData)
      .map(response => mapResponse[R](response))
  }

  def get[A](url: String, headers: List[(String, String)] = List(), queryParams: List[(String, String)] = List())(implicit fjs: Reads[A]): Future[A] =
  {
    Logger.debug(s"GET | url:[$url] | headers:[$headers]")
    ws
      .url(url)
      .withHeaders(headers: _*)
      .withQueryString(queryParams: _*)
      .get()
      .map(response => mapResponse(response))
      .map(option => option.get)
  }

  private def mapResponse[A](response: WSResponse)(implicit fjs: Reads[A]): Option[A] =
  {
    if (204.equals(response.status)) {
      return Option.empty[A]
    }

    if (!(200 to 299).contains(response.status)) {
      Logger.error(s"ERROR: ResponseCode:[${response.status}] | ResponseBody:[${response.body}]")
      throw HttpClientInternalException(response.body, response.status)
    }
    Option.apply(Json.parse(response.body).as[A])
  }
}
