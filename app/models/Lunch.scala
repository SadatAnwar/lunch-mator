package models

import java.sql.Timestamp
import play.api.libs.functional.syntax._
import play.api.libs.json._

object FormatTimestamp {

  implicit val rds: Reads[Timestamp] = (__ \ "time").read[Long].map { long => new Timestamp(long) }
  implicit val wrs: Writes[Timestamp] = (__ \ "time").write[Long].contramap { (a: Timestamp) => a.getTime }
  implicit val fmt: Format[Timestamp] = Format(rds, wrs)
}

case class Lunch(restaurant: RestaurantRow, size: Int, startTime: Timestamp)

case class LunchDto(restaurantId: Int, lunchName: String, startTime: String, anonymous: Boolean, maxSize: Int)

case class LunchRow(id: Option[Int], restaurantId: Int, maxSize: Int, startTime: Timestamp, anonymous: Boolean)
