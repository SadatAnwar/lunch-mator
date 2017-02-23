package models

import org.joda.time.DateTime

case class ChatMessage (id: Option[Int], lunchId: Int, authorId: Int, replyTo: Option[Int], CreatedAt: Option[DateTime], meesage: String)
