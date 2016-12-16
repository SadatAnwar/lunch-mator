package controllers

import play.api.mvc.{Action, AnyContent, Controller}

trait AuthorizationController extends Controller {

  def authorize(): Action[AnyContent]
}
