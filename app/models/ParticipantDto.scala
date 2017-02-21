package models

import org.joda.time.DateTime

case class ParticipantRow(lunchId: Int, userId: Int, joined: DateTime, active: Boolean = true)

case class ParticipantDto(firstName: String, lastName: String, joined: DateTime)
