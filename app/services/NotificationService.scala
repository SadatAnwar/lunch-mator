package services

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

import play.api.Logger

import com.google.inject.Inject
import exceptions.ParticipantService
import mappers.HipChatMapper
import models._

class NotificationService @Inject()(participantService: ParticipantService, hipChatService: HipChatService, userMatcherService: UserMatcherService, lunchService: LunchService)(implicit ec: ExecutionContext)
{

  def sendNewMessageNotification(lunchId: Int, authorName: String, comment: String): Unit =
  {
    getUsers(lunchId).foreach { users =>
      val pings = users.map(HipChatMapper.mapUsers)
      val future = hipChatService.sendMessage(HipChatMessageDto(pings, HipChatMessage(s"$authorName commented on lunch: ${lunchService.getLunchUrl(lunchId)}: $comment")))
      Await.result(future, 30000 millis);
    }
  }

  def sendReminderForLunch(lunchId: Int): Unit =
  {
    getUsers(lunchId).foreach { users =>
      val pings = users.map(HipChatMapper.mapUsers)
      val future = hipChatService.sendReminder(HipChatCommunication(pings, lunchId))
      Logger.info(s"Users:[$users] | Sending reminder")
      Await.result(future, 30000 millis);
    }
  }

  private def getUsers(lunchId: Int): Future[Seq[HipChatUser]] =
  {
    participantService.getParticipationDetails(lunchId).flatMap { participants =>

      Future.sequence(participants
        .map(_._2)
        .map(user => findUserMentionName(user)
          .filter(a => a != null)
        ))
    }
  }

  private def findUserMentionName(userRow: UserRow): Future[HipChatUser] =
  {
    hipChatService.getUsersWithNameIn(userRow.firstName).map { users =>
      if (users.length == 1) {
        users.head
      }
      else {
        val u = users.filter(user => userMatcherService.userNameLike(user, userRow.lastName)).toList
        if (u.length == 1) {
          u.head
        } else {
          null
        }
      }
    }
  }
}
