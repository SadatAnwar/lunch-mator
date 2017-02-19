package models

import org.joda.time.DateTime

trait Lunch
{
  def id: Int

  def lunchName: String

  def restaurant: RestaurantDto

  def maxSize: Int

  def spotsLeft: Int

  def startTime: DateTime

  def joined: Boolean

  def anonymous: Boolean

  def active: Boolean
}

case class LunchDto(override val id: Int, override val lunchName: String, override val restaurant: RestaurantDto, override val maxSize: Int, override val spotsLeft: Int, override val startTime: DateTime, override val joined: Boolean, override val anonymous: Boolean, override val active: Boolean) extends Lunch

case class MyLunchDto(override val id: Int, override val lunchName: String, override val restaurant: RestaurantDto, override val maxSize: Int, override val spotsLeft: Int, override val startTime: DateTime, override val joined: Boolean, override val anonymous: Boolean, override val active: Boolean) extends Lunch

case class LunchAtRestaurant(lunch: LunchRow, restaurant: RestaurantRow)

case class LunchDetailDto(override val id: Int, override val lunchName: String, override val restaurant: RestaurantDto, override val maxSize: Int, override val spotsLeft: Int, override val startTime: DateTime, override val joined: Boolean, override val anonymous: Boolean, override val active: Boolean, participants: Seq[ParticipantDto]) extends Lunch

case class CreateLunchDto(restaurantId: Int, lunchName: Option[String], startTime: Long, anonymous: Option[Boolean], maxSize: Int)

case class LunchRow(id: Option[Int], lunchName: Option[String], restaurantId: Int, maxSize: Int, startTime: DateTime, anonymous: Boolean, active: Boolean)
