package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

import models.Formats._

case class Page[T](items: Seq[T], links: Links, maxResults: Int, startIndex: Int)

object Page {

  implicit def pageFormat[T: Format]: Format[Page[T]] =
    ((__ \ "items").format[Seq[T]] ~
      (__ \ "links").format[Links] ~
      (__ \ "maxResults").format[Int] ~
      (__ \ "startIndex").format[Int]
      ) (Page.apply, unlift(Page.unapply))
}

case class Links(self: String, next: Option[String], prev: Option[String])
