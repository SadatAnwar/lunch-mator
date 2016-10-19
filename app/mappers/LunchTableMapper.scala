package mappers

import models.{CreateLunchDto, Lunch, LunchRow}
import org.joda.time.DateTime

object LunchTableMapper {

  def map(lunchDto: CreateLunchDto): LunchRow = {
    LunchRow(None, lunchDto.restaurantId, lunchDto.maxSize, new DateTime(lunchDto.startTime), lunchDto.anonymous)
  }

  def map(lunchRows: Seq[LunchRow]): Seq[CreateLunchDto] = {
    lunchRows.map(map(_))
  }

  def map(lunchRow: LunchRow): CreateLunchDto = {
    CreateLunchDto(lunchRow.restaurantId, "", lunchRow.startTime.getMillis(), lunchRow.anonymous, lunchRow.maxSize)
  }
}
