package services

import java.text.{Normalizer, SimpleDateFormat}
import java.util.Date
import java.util.regex.Pattern

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.Configuration
import play.api.Logger

import client.{HipChatMessagingClient, HipChatUserCacheClient}
import com.google.inject.Inject
import models._
import org.joda.time.{DateTime, Days}

class HipChatService @Inject()(configuration: Configuration,
                               hipChatUserClient: HipChatUserCacheClient,
                               hipChatMessagingClient: HipChatMessagingClient,
                               lunchService: LunchService,
                               userService: UserService)
{
  private val lunchMatorHost = configuration.getString("lunchmator.host")

  def getUsersWithNameIn(username: String, name: String): Future[Seq[HipChatUser]] =
  {
    Logger.info(s"HipChat user search: search by:[$username] | search for:[$name]")
    hipChatUserClient.getAllUsers.map { users =>
      users.filter(user => userNameLike(user, name))
    }
  }

  def sendInvitation(invitation: InvitationDto): Future[String] =
  {
    lunchService.getLunchAndRestaurant(invitation.lunchId).flatMap { result =>

      val message = makeInvitation(result._1, result._2)
      hipChatMessagingClient.sendMessage(invitation.users, message)
    }
  }

  def sendMessage(hipChatMessageDto: HipChatMessageDto): Future[String] =
  {
    hipChatMessagingClient.sendMessage(hipChatMessageDto.users, hipChatMessageDto.message)
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

  private def userNameLike(user: HipChatUser, searchString: String) =
  {
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    val userName = pattern.matcher(Normalizer.normalize(user.name, Normalizer.Form.NFD).toLowerCase).replaceAll("")
    val search = pattern.matcher(Normalizer.normalize(searchString, Normalizer.Form.NFD).toLowerCase).replaceAll("")
    val mentionName = pattern.matcher(Normalizer.normalize(user.mention_name, Normalizer.Form.NFD).toLowerCase).replaceAll("")

    userName.contains(search.toLowerCase) || mentionName.contains(search.toLowerCase)
  }
}
