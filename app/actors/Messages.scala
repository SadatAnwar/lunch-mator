package actors

import models.UserRow

class Messages
{
}

case class LunchReminderMessage(lunchId: Int)

case class NewLunchCreatedMessage(lunchId: Int, user: UserRow)

case class ParticipantLeftMessage(lunchId: Int)

case class RescheduleAllReminders()
