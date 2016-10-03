package models

import java.sql.Timestamp

import org.joda.time.DateTime

import scala.util.parsing.json.JSONFormat

case class LunchTable(restaurant: Restaurant, size: Int)


case class LunchTableRow(id: Option[Int],
                         restaurantId: Int,
                         maxSize: Int,
                         startTime: Option[Timestamp],
                         anonymous: Option[Boolean]
                        )
