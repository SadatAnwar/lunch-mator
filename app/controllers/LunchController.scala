package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import services.LunchService
import scala.concurrent.ExecutionContext.Implicits.global

class LunchController @Inject()(lunchService: LunchService) extends Controller {

  def getLunchById(id: String) = Action.async { request =>
    lunchService.getLunchById(id.toInt).map(lunch =>
      Ok
    )
  }
}
