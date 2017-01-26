package controllers

import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider
import play.api.mvc.{Action, AnyContent, Controller, EssentialAction}

import services.{Authenticated, UserService}

class Application @Inject()(dbConfigProvider: DatabaseConfigProvider, userService: UserService) extends Controller {

  def secured(): EssentialAction = Authenticated.async {
    request =>
      Future.successful(Ok(views.html.index()))
  }

  def descriptor(): Action[AnyContent] = Action.async {
    Future.successful(Ok("{\n  \"description\": \"description of your add-on\", \n  \"key\": \"your-addon-key\", \n  \"name\": \"Your addon name\", \n  \"vendor\": {\n    \"name\": \"Your company name\", \n    \"url\": \"https://www.your-company.com\"\n  }\n  \"links\": {\n    \"self\": \"https://your-addon-url/descriptor-url\"\n  }, \n  \"capabilities\": {\n    \"hipchatApiConsumer\": {\n      \"scopes\": [\n        \"send_notification\"\n      ]\n    }, \n    \"installable\": {\n      \"allowGlobal\": false, \n      \"allowRoom\": true, \n      \"installedUrl\": \"https://your-addon-url/installed\",\n      \"updatedUrl\": \"https://your-addon-url/updated\"\n    }\n}"))
  }

  def securedWithParam(id: Any): EssentialAction = Authenticated.async {
    request =>
      Future.successful(Ok(views.html.index()))
  }

  def unSecure(): Action[AnyContent] = Action.async { request =>
    val params = request.queryString.map { case (k, v) => k -> v.mkString }
    val origin = params.getOrElse("origin", "/welcome")
    if (request.session.get("email").isDefined) {
      userService.validateUser(request.session.get("email").get).map { valid =>
        if (valid) {
          Redirect("/welcome")
        } else {
          Ok(views.html.index())
        }
      }
    } else {
      Future.successful(Ok(views.html.index()))
    }
  }
}
