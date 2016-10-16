package models

import java.sql.Timestamp

case class Lunch(restaurant: RestaurantRow, size: Int)

case class LunchDto(restaurantId: Int, lunchName: String, startTime: String, anonymous: Boolean, maxSize: Int)

case class LunchTableRow(id: Option[Int], restaurantId: Int, maxSize: Int, startTime: Timestamp, anonymous: Boolean)

