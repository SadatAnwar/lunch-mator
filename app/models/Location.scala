package models

import slick.driver.PostgresDriver.api._

case class Location(id: Int, lat: Double, lon: Double)

class Locations(tag: Tag) extends Table[Location](tag, "Locations") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def lat = column[Double]("lat")
  def lon = column[Double]("lon")

  def * = (id, lat, lon) <> (Location.tupled, Location.unapply _)
}
