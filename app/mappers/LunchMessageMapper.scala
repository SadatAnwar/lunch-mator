package mappers

import models.LunchComment
import org.joda.time.DateTime

object LunchMessageMapper
{
  def map(messageText: String, lunchId: Int, authorId: Int): LunchComment =
  {
    LunchComment(None, lunchId, authorId, None, Some(DateTime.now()), messageText)
  }
}
