package models

case class HipChatUser(id: Long, links: Links, mention_name: String, name: String, version: String)

case class HipChatPing(mention_name: String)

case class HipChatMessage(message: String, color: String = "green", message_format: String = "text")

case class HipChatMessageDto(users: List[HipChatPing], message: HipChatMessage)

case class InvitationDto(users: List[HipChatPing], lunchId: Int)
