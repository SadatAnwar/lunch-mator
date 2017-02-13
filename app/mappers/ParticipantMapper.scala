package mappers

import models._

object ParticipantMapper
{

  def map(participantRow: ParticipantRow, userRow: UserRow, lunchRow: LunchRow): ParticipantDto =
  {
    var firstName = userRow.firstName
    var lastName = userRow.lastName
    if (lunchRow.anonymous) {
      firstName = "Anonymous"
      lastName = "User"
    }
    ParticipantDto(firstName, lastName, participantRow.joined)
  }

  def map(participantDetails: Seq[(ParticipantRow, UserRow, LunchRow, RestaurantRow)]): Seq[ParticipantDto] =
  {
    participantDetails.map(p => map(p._1, p._2, p._3))
  }
}
