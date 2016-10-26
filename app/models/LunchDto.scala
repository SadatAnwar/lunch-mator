package models

import org.joda.time.DateTime

case class LunchDto(id: Int, lunchName: String, restaurant: RestaurantDto, maxSize: Int, spotsLeft: Int, startTime: DateTime, anonymous: Boolean)

case class LunchDetailDto(id: Int, lunchName: String, restaurant: RestaurantDto, maxSize: Int, spotsLeft: Int, startTime: DateTime,anonymous: Boolean, participants: Seq[ParticipantDto])

case class CreateLunchDto(restaurantId: Int, lunchName: Option[String], startTime: Long, anonymous: Option[Boolean], maxSize: Int)

case class LunchRow(id: Option[Int], lunchName: Option[String], restaurantId: Int, maxSize: Int, startTime: DateTime, anonymous: Boolean)
