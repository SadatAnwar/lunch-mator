package controllers

import javax.inject.Singleton

import mapper.ErrorMessageMapper
import org.postgresql.util.PSQLException
import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc._
import models.Formats._

import scala.concurrent._

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    val errorMessage = exception match {
      case sqlException: PSQLException => ErrorMessageMapper.map(sqlException, request.path)
      case exception: Exception => ErrorMessageMapper.map(exception)
    }
    Future.successful(
      InternalServerError(Json.toJson(errorMessage))
    )
  }
}
