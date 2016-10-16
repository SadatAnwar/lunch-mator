package models

case class Restaurant(name: String, addedBy: UserRow)

case class RestaurantRow(id: Option[Int], name: String, website: Option[String], description: Option[String], addedByUserId: Int)

case class RestaurantDto(name: String, website: Option[String], description: Option[String])
