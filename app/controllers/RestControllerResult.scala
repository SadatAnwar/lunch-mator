package controllers

import play.api.libs.ws.WSResponse
import play.api.mvc.Results

trait RestControllerResult {
  def autoResult(response: WSResponse) = Results.Status(response.status)(response.json)
}
