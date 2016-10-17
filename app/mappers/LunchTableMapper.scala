package mappers

import java.sql.Timestamp
import java.text.SimpleDateFormat
import models.{LunchDto, LunchRow}

object LunchTableMapper {

  def map(lunchDto: LunchDto): LunchRow = {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    LunchRow(None, lunchDto.restaurantId, lunchDto.maxSize, new Timestamp(format.parse(lunchDto.startTime).getTime), lunchDto.anonymous)
  }
}
