package mappers

import models.{HipChatMessage, HipChatUser}

object HipChatMapper {

  def mapNotificationMessage(users: Seq[HipChatUser], hipChatMessage: HipChatMessage): HipChatMessage = {
    var finalMessage = hipChatMessage.message
    if (users.nonEmpty) {
      finalMessage = hipChatMessage.message.concat("\n/cc: ")
    }

    users.foreach { user =>
      finalMessage = finalMessage.concat(s" @${user.mention_name}")
    }

    HipChatMessage(finalMessage, hipChatMessage.color, hipChatMessage.message_format)
  }
}
