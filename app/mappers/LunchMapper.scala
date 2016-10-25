package mappers

import models._
import org.joda.time.DateTime
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

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
    Lunch(l.id.getOrElse(-1), l.lunchName.getOrElse(""), RestaurantMapper.map(r), l.maxSize, l.maxSize - participantCount, l.startTime, l.anonymous)
  }

  def map(lunchRow: LunchRow, restaurantRow: RestaurantRow, participantDtos: Seq[ParticipantDto]): LunchDetailDto = {
    LunchDetailDto(lunchRow.lunchName.getOrElse(""), RestaurantMapper.map(restaurantRow), lunchRow.startTime, participantDtos)
  }

  def map(lunchRow: LunchRow, restaurantRow: RestaurantRow, participantDtos: Future[Seq[ParticipantDto]]) = {
    participantDtos.map { participants =>
      LunchDetailDto(lunchRow.lunchName.getOrElse(""), RestaurantMapper.map(restaurantRow), lunchRow.startTime, participants)
    }
  }
}
