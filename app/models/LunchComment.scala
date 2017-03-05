package models

import org.joda.time.DateTime

case class LunchComment(id: Option[Int], lunchId: Int, authorId: Int, replyToMessage: Option[Int], createdAt: Option[DateTime], message: String)

case class NewComment(text: String)
