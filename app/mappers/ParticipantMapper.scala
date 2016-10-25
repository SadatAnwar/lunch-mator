package mappers

import models._

object ParticipantMapper {

  def map(participantRow: ParticipantRow, userRow: UserRow): ParticipantDto = {
    ParticipantDto(userRow.firstName, participantRow.joined)
  }
}
