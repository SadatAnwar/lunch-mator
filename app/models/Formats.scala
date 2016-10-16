package models

import play.api.libs.json._

object Formats {

  implicit val userFormat = Json.format[UserRow]
  implicit val userIdentityFormat = Json.format[UserIdentity]
  implicit val newUserDtoFormat = Json.format[NewUserDto]

  implicit val restaurantFormat = Json.format[Restaurant]
  implicit val restaurantDtoFormat = Json.format[RestaurantDto]
  implicit val restaurantRowFormat = Json.format[RestaurantRow]

  implicit val lunchDto = Json.format[LunchDto]
  implicit val random = Json.format[LunchDto]
  implicit val lunchFormat = Json.format[Lunch]

  implicit val errorFormat = Json.format[Error]

  implicit val userRest = Json.format[(UserRow, RestaurantRow)]
}
