package mappers

import models._
import org.joda.time.DateTime

object LunchMapper
{

  def map(lunchDto: CreateLunchDto): LunchRow =
  {
    LunchRow(None, lunchDto.lunchName, lunchDto.restaurantId, lunchDto.maxSize, new DateTime(lunchDto.startTime), lunchDto.anonymous.getOrElse(false), active = true)
  }

  def map(lunchRows: Seq[LunchRow]): Seq[CreateLunchDto] =
  {
    lunchRows.map(map)
  }

  def map(lunchRow: LunchRow): CreateLunchDto =
  {
    CreateLunchDto(lunchRow.restaurantId, lunchRow.lunchName, lunchRow.startTime.getMillis, Some(lunchRow.anonymous), lunchRow.maxSize)
  }

  def map(l: LunchRow, r: RestaurantRow, joined: Int, participantCount: Int): LunchDto =
  {
    LunchDto(l.id.getOrElse(-1), l.lunchName.getOrElse(""), RestaurantMapper.map(r), l.maxSize, l.maxSize - participantCount, l.startTime, joined == 1, anonymous = l.anonymous, active = l.active)
  }

  def map(l: LunchRow, r: RestaurantRow): MyLunchDto =
  {
    MyLunchDto(l.id.getOrElse(-1), l.lunchName.getOrElse(""), RestaurantMapper.map(r), l.maxSize, 0, l.startTime, joined = true, anonymous = l.active, active = l.anonymous)
  }
}
