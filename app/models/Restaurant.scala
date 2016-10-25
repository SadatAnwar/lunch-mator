package models

case class Restaurant(name: String, addedBy: UserRow)

case class RestaurantRow(id: Option[Int], name: String, website: String, description: Option[String], addedByUserId: Int)

case class RestaurantDto(id: Int, name: String, website: Option[String], description: Option[String])

case class CreateRestaurantDto(name: String, website: Option[String], description: Option[String])
