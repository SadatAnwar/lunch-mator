package models

import org.joda.time.DateTime

case class Lunch(id: Int, restaurant: RestaurantRow, size: Int, startTime: DateTime, anonymous: Boolean)

case class Lunch2(id: Int, restaurant: RestaurantRow, size: Int, spotsLeft: Int, startTime: DateTime, anonymous: Boolean)

case class CreateLunchDto(restaurantId: Int, lunchName: Option[String], startTime: Long, anonymous: Option[Boolean], maxSize: Int)

case class LunchRow(id: Option[Int], lunchName: Option[String], restaurantId: Int, maxSize: Int, startTime: DateTime, anonymous: Boolean)
