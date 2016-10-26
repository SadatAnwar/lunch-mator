package models

import org.joda.time.DateTime

case class ParticipantRow(lunchId: Int, userId: Int, joined: DateTime)

case class ParticipantDto(userName: String, joined: DateTime)
