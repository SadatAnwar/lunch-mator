package mappers

import models._
import org.joda.time.DateTime

object LunchMapper {

  def map(lunchDto: CreateLunchDto): LunchRow = {
    LunchRow(None, lunchDto.lunchName, lunchDto.restaurantId, lunchDto.maxSize, new DateTime(lunchDto.startTime), lunchDto.anonymous.getOrElse(false))
  }

  def map(lunchRows: Seq[LunchRow]): Seq[CreateLunchDto] = {
    lunchRows.map(map)
  }

  def map(lunchRow: LunchRow): CreateLunchDto = {
    CreateLunchDto(lunchRow.restaurantId, lunchRow.lunchName, lunchRow.startTime.getMillis, Some(lunchRow.anonymous), lunchRow.maxSize)
  }

  def map(l: LunchRow, r: RestaurantRow, participantCount: Int) = {
    LunchDto(l.id.getOrElse(-1), l.lunchName.getOrElse(""), RestaurantMapper.map(r), l.maxSize, l.maxSize - participantCount, l.startTime, l.anonymous)
  }

  def map(l: LunchRow, r: RestaurantRow) = {
    MyLunchDto(l.id.getOrElse(-1), l.lunchName.getOrElse(""), RestaurantMapper.map(r), l.maxSize, l.startTime, l.anonymous)
  }

  def map(lunchRow: LunchRow, restaurantRow: RestaurantRow, participantDtos: Seq[ParticipantDto]): LunchDetailDto = {
    val participantCount = participantDtos.length
    val participants = participantDtos

    if (lunchRow.anonymous) {
      participants.map(p => ParticipantDto("Anonymous User", p.joined))
    }

    LunchDetailDto(lunchRow.id.getOrElse(-1), lunchRow.lunchName.getOrElse(""), RestaurantMapper.map(restaurantRow), lunchRow.maxSize, lunchRow.maxSize - participantCount, lunchRow.startTime, lunchRow.anonymous, participants)
  }
}
