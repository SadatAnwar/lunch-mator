package models


import argonaut._, Argonaut._

case class Restaurant(id: Option[Int], name: String, location: Int)

object Restaurant {
  implicit def PersonCodecJson: CodecJson[Restaurant] =
    casecodec4(Restaurant.apply, Restaurant.unapply)("id", "name", "location")
}

