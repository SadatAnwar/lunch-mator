package actors.messages

import models.UserRow

case class NewLunchCreatedMessage(lunchId: Int, user: UserRow)

