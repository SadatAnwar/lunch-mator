package models

import java.sql.Timestamp

case class Participant(lunchId: Int, userId: Int, joined: Timestamp)

case class ParticipantRow(lunchId: Int, userId: Int, joined: Timestamp)
