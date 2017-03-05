package actors.messages

import models.UserRow

object Message
{

  case class LunchReminderMessage(lunchId: Int)

  case class NewLunchCreatedMessage(lunchId: Int, user: UserRow)

  case class ParticipantLeftMessage(lunchId: Int)

  case class NewCommentMessage(lunchId: Int, author: String, comment: String)

}
