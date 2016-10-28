package models

import java.sql.Timestamp

import play.api.libs.functional.syntax._
import play.api.libs.json._

object Formats {
  implicit val rds: Reads[Timestamp] = (__ \ "startTime").read[Long].map { long => new Timestamp(long) }
  implicit val wrs: Writes[Timestamp] = (__ \ "startTime").write[Long].contramap { (a: Timestamp) => a.getTime }
  implicit val fmt: Format[Timestamp] = Format(rds, wrs)

  implicit val userFormat = Json.format[UserRow]
  implicit val userIdentityFormat = Json.format[UserIdentity]
  implicit val newUserDtoFormat = Json.format[NewUserDto]

  implicit val restaurantFormat = Json.format[Restaurant]
  implicit val restaurantDtoFormat = Json.format[RestaurantDto]
  implicit val createRestaurantDtoFormat = Json.format[CreateRestaurantDto]
  implicit val restaurantRowFormat = Json.format[RestaurantRow]

  implicit val participantDto = Json.format[ParticipantDto]

  implicit val lunchFormat = Json.format[LunchDto]
  implicit val lunchDetailFormat = Json.format[LunchDetailDto]

  implicit val lunchDtoFormat = Json.format[CreateLunchDto]

  implicit val errorFormat = Json.format[Error]

  implicit val userRest = Json.format[(UserRow, RestaurantRow)]
}
