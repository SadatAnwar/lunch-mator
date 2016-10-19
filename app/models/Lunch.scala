package models

import org.joda.time.DateTime

case class Lunch(id: Int, restaurant: RestaurantRow, size: Int, startTime: DateTime, anonymous: Boolean)

case class CreateLunchDto(restaurantId: Int, lunchName: String, startTime: Long, anonymous: Boolean, maxSize: Int)

case class LunchRow(id: Option[Int], restaurantId: Int, maxSize: Int, startTime: DateTime, anonymous: Boolean)
