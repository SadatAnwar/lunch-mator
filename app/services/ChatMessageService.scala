package services

import scala.concurrent.{ExecutionContext, Future}

import play.api.db.slick.DatabaseConfigProvider

import com.google.inject.Inject
import mappers.LunchMessageMapper
import models.{LunchComment, UserRow}
import persistence.repository.LunchComments

class ChatMessageService @Inject()(implicit ec: ExecutionContext, dbConfigProvider: DatabaseConfigProvider) extends Service
{
  def getCommentsForLunch(lunchId: Int): Future[Seq[LunchComment]] = usingDB {
    LunchComments.getMessagesForLunch(lunchId)
  }

  def postComment(messageText: String, lunchId: Int)(implicit user: UserRow): Future[Int] = usingDB {
    val message = LunchMessageMapper.map(messageText, lunchId, user.id.get)
    LunchComments.postNewMessage(message)
  }
}
