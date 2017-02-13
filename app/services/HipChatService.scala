package services

import java.text.SimpleDateFormat
import java.util.Date

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.Configuration
import play.api.Logger

import client.{HipChatMessagingClient, HipChatUserCacheClient}
import com.google.inject.Inject
import models._
import org.joda.time.{DateTime, Days, Minutes}

class HipChatService @Inject()(configuration: Configuration,
                               hipChatUserClient: HipChatUserCacheClient,
                               hipChatMessagingClient: HipChatMessagingClient,
                               lunchService: LunchService,
                               userMatcherService: UserMatcherService,
                               userService: UserService)
{
  private val lunchMatorHost = configuration.getString("lunchmator.host")

  def getUsersWithNameIn(name: String): Future[Seq[HipChatUser]] =
  {
    hipChatUserClient.getAllUsers.map { users =>
      users.filter(user => userMatcherService.userNameLike(user, name))
    }
  }

  def sendInvitation(communication: HipChatCommunication)(implicit userName: String): Future[String] =
  {
    Logger.info(s"Sending HipChat invitation | From:[$userName] | To:[${communication.users.map(_.mention_name).mkString(",")}]")

    lunchService.getLunchAndRestaurant(communication.lunchId).flatMap { result =>
      val message = makeInvitation(result._1, result._2)

      hipChatMessagingClient.sendMessage(communication.users, message)
    }
  }

  def sendReminder(communication: HipChatCommunication): Future[String] =
  {
    lunchService.getLunchAndRestaurant(communication.lunchId).flatMap { result =>
      val message = makeReminder(result._1, result._2)

      Logger.info(s"Sending LunchReminder | For LunchId:[${communication.lunchId}] | To:[${communication.users.map(_.mention_name).mkString(",")}]")

      hipChatMessagingClient.sendMessage(communication.users, message)
    }
  }

  def sendMessage(hipChatMessageDto: HipChatMessageDto): Future[String] =
  {
    hipChatMessagingClient.sendMessage(hipChatMessageDto.users, hipChatMessageDto.message)
  }

  private def makeReminder(lunch: LunchRow, restaurant: RestaurantRow): HipChatMessage =
  {
    val timeLeft = Minutes.minutesBetween(DateTime.now(), lunch.startTime).getMinutes
    val timeFormat = new SimpleDateFormat("hh:mm a")

    val reminderMessage = s"Hey! Just wanted to remind you about your lunch ($lunchMatorHost/lunch/${lunch.id.get}) " +
      s"in $timeLeft min (${timeFormat.format(new Date(lunch.startTime.getMillis))}) at ${restaurant.name} ${restaurant.website}."

    HipChatMessage(reminderMessage, color = HipChatMessageColor.YELLOW)
  }

  private def makeInvitation(lunch: LunchRow, restaurant: RestaurantRow): HipChatMessage =
  {
    val daysInBetween = Days.daysBetween(DateTime.now.withTimeAtStartOfDay(), lunch.startTime.withTimeAtStartOfDay()).getDays
    val timeFormat = new SimpleDateFormat("hh:mm a")
    val dayFormat = new SimpleDateFormat("dd/MM")

    var when = s"on ${dayFormat.format(new Date(lunch.startTime.getMillis))} " +
      s"at ${timeFormat.format(new Date(lunch.startTime.getMillis))}"

    if (daysInBetween == 0) {
      when = s"today at ${timeFormat.format(new Date(lunch.startTime.getMillis))}"
    }

    if (daysInBetween == 1) {
      when = s"tomorrow at ${timeFormat.format(new Date(lunch.startTime.getMillis))}"
    }

    val invitationMessage = s"Hello! You have been invited for a lunch $when at ${restaurant.name}, " +
      s"to join, click $lunchMatorHost/lunch/${lunch.id.get}"

    HipChatMessage(invitationMessage)
  }
}
