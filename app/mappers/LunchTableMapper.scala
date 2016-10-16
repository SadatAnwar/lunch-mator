package mappers

import java.sql.Timestamp
import java.text.SimpleDateFormat
import models.{LunchDto, LunchTableRow}

object LunchTableMapper {

  def map(lunchDto: LunchDto): LunchTableRow = {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    LunchTableRow(None, lunchDto.restaurantId, lunchDto.maxSize, new Timestamp(format.parse(lunchDto.startTime).getTime), lunchDto.anonymous)
  }
}
