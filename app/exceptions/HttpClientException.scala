package exceptions

case class HttpClientException(requestUrl: String, requestBody: Option[String], response: String, responseStatus: Int) extends RuntimeException

case class HttpClientInternalException(response: String, responseStatus: Int) extends RuntimeException
