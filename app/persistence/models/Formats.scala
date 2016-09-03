package persistence.models

import play.api.libs.json.Json

object Formats {

  implicit val restaurantFormat = Json.format[Restaurant]
}
