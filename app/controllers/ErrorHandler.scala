package controllers

import javax.inject.Singleton

import exceptions.PasswordValidationException
import mappers.ErrorMessageMapper
import models.Formats._
import org.postgresql.util.PSQLException
import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent._

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Logger.error(s"Client error: {$request}")
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    val errorMessage = exception match {
      case e: PSQLException => ErrorMessageMapper.map(e, request.path)
      case e: PasswordValidationException => ErrorMessageMapper.map(e)
      case e: Exception => ErrorMessageMapper.map(e)
    }
    Future.successful(
      InternalServerError(Json.toJson(errorMessage))
    )
  }
}
