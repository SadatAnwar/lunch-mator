package exceptions

case class RestaurantNotFoundException(id: Int) extends EntityNotFoundException(s"RestaurantId:[$id]") {
}
