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

  def map(l: LunchRow, r: RestaurantRow, joined: Int, participantCount: Int): LunchDto = {
    LunchDto(l.id.getOrElse(-1), l.lunchName.getOrElse(""), RestaurantMapper.map(r), l.maxSize, l.maxSize - participantCount, l.startTime, joined==0, l.anonymous)
  }

  def map(l: LunchRow, r: RestaurantRow) = {
    MyLunchDto(l.id.getOrElse(-1), l.lunchName.getOrElse(""), RestaurantMapper.map(r), l.maxSize, l.startTime, l.anonymous)
  }

  def map(lunchRow: LunchRow, restaurantRow: RestaurantRow, participantDtos: Seq[ParticipantDto]): LunchDetailDto = {
    val participantCount = participantDtos.length

    if (lunchRow.anonymous) {
      LunchDetailDto(lunchRow.id.getOrElse(-1), lunchRow.lunchName.getOrElse(""), RestaurantMapper.map(restaurantRow), lunchRow.maxSize, lunchRow.maxSize - participantCount, lunchRow.startTime, lunchRow.anonymous, participantDtos.map(p => ParticipantDto("Anonymous User", p.joined)))
    } else {
      LunchDetailDto(lunchRow.id.getOrElse(-1), lunchRow.lunchName.getOrElse(""), RestaurantMapper.map(restaurantRow), lunchRow.maxSize, lunchRow.maxSize - participantCount, lunchRow.startTime, lunchRow.anonymous, participantDtos)
    }
  }
}
