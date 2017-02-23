package services

import scala.concurrent.{ExecutionContext, Future}

import com.google.inject.Inject
import models.{ChatMessage, UserRow}
import persistence.repository.ChatMessages

class ChatMessageService @Inject()(implicit ec: ExecutionContext)
{
  def getMessagesForLunch(lunchId: Int): Future[Seq[ChatMessage]] = usingDB {
    ChatMessages.getMessagesForLunch(lunchId)
  }

  def postMessage(message: String)(implicit user: UserRow) =
  {
  }
}
