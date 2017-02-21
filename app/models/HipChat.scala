package models

case class HipChatUser(id: Long, links: Links, mention_name: String, name: String, version: String)

case class HipChatPing(mention_name: String)

case class HipChatMessage(message: String, color: String = HipChatMessageColor.GREEN, message_format: String = "text")

case class HipChatMessageDto(users: Seq[HipChatPing], message: HipChatMessage)

case class HipChatCommunication(users: Seq[HipChatPing], lunchId: Int)

object HipChatMessageColor extends Enumeration
{
  val GREEN = "green"
  val YELLOW = "yellow"
  val RED = "red"
}
