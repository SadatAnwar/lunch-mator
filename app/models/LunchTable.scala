package models

import java.sql.Timestamp

import org.joda.time.DateTime

import scala.util.parsing.json.JSONFormat

class LunchTable(owner: User, restaurant: Restaurant, startTime: DateTime, anonymous: Boolean) {

  def this(user: User, restaurant: Restaurant) = this(user, restaurant, DateTime.now(), true)

  def read(json: String) = this

  def writes() = {
    "random bull shit"
  }
}

case class LunchTableRow(id: Option[Int],
                         ownerId: Int,
                         restaurantId: Int,
                         maxSize: Int,
                         startTime: Timestamp,
                         anonymous: Boolean
                        )
