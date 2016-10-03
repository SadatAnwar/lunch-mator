package models

import play.api.libs.json._

object Formats {

  implicit val restaurantFormat = Json.format[Restaurant]

  implicit val lunchFormat = Json.format[LunchTable]

  implicit val userIdentityFormat = Json.format[UserIdentity]

  implicit val userFormat = Json.format[User]

  implicit val errorFormat = Json.format[Error]

  implicit val userRest = Json.format[(User, Restaurant)]
}
