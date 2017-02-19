package mappers

import models._

object RestaurantMapper
{

  def map(restaurantRow: RestaurantRow, user: UserRow) =
  {
    Restaurant(restaurantRow.name, user)
  }

  def map(restaurantDto: CreateRestaurantDto, user: UserRow) =
  {
    RestaurantRow(None, restaurantDto.name, restaurantDto.website.getOrElse(""), restaurantDto.description, user.id.get)
  }

  def map(restaurantRow: RestaurantRow): RestaurantDto =
  {
    RestaurantDto(restaurantRow.id.getOrElse(-1), restaurantRow.name, Some(restaurantRow.website), restaurantRow.description)
  }
}
