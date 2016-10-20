package mappers

import models.{CreateLunchDto, LunchRow}
import org.joda.time.DateTime

object LunchTableMapper {

  def map(lunchDto: CreateLunchDto): LunchRow = {
    LunchRow(None, lunchDto.lunchName, lunchDto.restaurantId, lunchDto.maxSize, new DateTime(lunchDto.startTime), lunchDto.anonymous.getOrElse(false))
  }

  def map(lunchRows: Seq[LunchRow]): Seq[CreateLunchDto] = {
    lunchRows.map(map(_))
  }

  def map(lunchRow: LunchRow): CreateLunchDto = {
    CreateLunchDto(lunchRow.restaurantId, lunchRow.lunchName, lunchRow.startTime.getMillis(), Some(lunchRow.anonymous), lunchRow.maxSize)
  }
}
