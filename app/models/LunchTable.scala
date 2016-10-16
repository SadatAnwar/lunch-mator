package models

import java.sql.Timestamp

case class LunchTable(restaurant: RestaurantRow, size: Int)


case class LunchTableRow(id: Option[Int],
                         restaurantId: Int,
                         maxSize: Int,
                         startTime: Option[Timestamp],
                         anonymous: Option[Boolean]
                        )
