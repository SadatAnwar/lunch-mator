package mappers

import models._

object RestaurantMapper {

  def map(restaurantRow: RestaurantRow, user: UserRow) = {
    Restaurant(restaurantRow.name, user)
  }

  def map(restaurantDto: RestaurantDto, user: User) = {
    RestaurantRow(None, restaurantDto.name, restaurantDto.website, restaurantDto.description, user.id)
  }
}
