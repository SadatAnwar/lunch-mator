package controllers

import javax.inject.Singleton

import scala.concurrent._

import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc._

import exceptions.{AuthenticationException, EntityNotFoundException}
import mappers.ErrorMessageMapper
import models.Formats._
import org.postgresql.util.PSQLException

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Status] = {
    Logger.error(s"Client error: message:[$message], request:[$request], status:[$statusCode]")
    Future.successful(
      Status(statusCode)
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    val errorMessage = exception match {

      case e: AuthenticationException => return Future.successful(makeLoginRedirect(e.origin))
      case _: java.util.NoSuchElementException => return Future.successful(NotFound("Requested content not found"))
      case e: EntityNotFoundException => return Future.successful(NotFound(e.getMessage))
      case e: PSQLException => ErrorMessageMapper.map(e, request.path)
      case e: Exception => ErrorMessageMapper.map(e)
    }
    Logger.error(s"Server side error for request [$request] exception thrown: [$exception] ${exception.getMessage} ${exception.printStackTrace()}")
    Future.successful(
        InternalServerError(Json.toJson(errorMessage))
    )
  }

  private def makeLoginRedirect(origin: String): Result = {
    Redirect(s"/login?origin=$origin").withNewSession
  }
}
