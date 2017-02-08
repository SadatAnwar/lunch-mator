package services

import java.text.{Normalizer, SimpleDateFormat}
import java.util.Date
import java.util.regex.Pattern

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.Configuration
import play.api.Logger
import play.api.libs.json.{JsValue, Json}
import play.mvc.Http

import client.{HipChatUserCacheClient, RestClientWrapper}
import com.google.inject.Inject
import mappers.HipChatMapper
import models.Formats._
import models._
import org.joda.time.{DateTime, Days}

class HipChatService @Inject()(configuration: Configuration,
                               restClient: RestClientWrapper,
                               hipChatUserClient: HipChatUserCacheClient,
                               lunchService: LunchService,
                               userService: UserService)
{

  private val lunchMatorHost = configuration.getString("lunchmator.host")
  private val apiBaseUrl = configuration.getString("hipchat.api.baseurl")
  private val writeToken = configuration.getString("hipchat.lunchmator.write.accesstoken")
  private val lunchMatorRoomId = configuration.getString("hipchat.lunchmator.chatroom.id")

  def getUsersWithNameIn(name: String): Future[Seq[HipChatUser]] =
  {
    hipChatUserClient.getAllUsers.map { users =>
      users.filter(user => userNameLike(user, name))
    }
  }

  def sendInvitation(invitation: InvitationDto): Future[String] =
  {
    lunchService.getLunchWithId(invitation.lunchId).flatMap { result =>
      val lunch = result._1
      val restaurant = result._2
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
        s"to join, click $lunchMatorHost/${lunch.id.get}"

      sendMessage(invitation.users, HipChatMessage(invitationMessage))
    }
  }

  def sendMessage(hipChatMessageDto: HipChatMessageDto): Future[String] =
  {
    sendMessage(hipChatMessageDto.users, hipChatMessageDto.message)
  }

  private def sendMessage(users: Seq[HipChatPing], hipchatMessage: HipChatMessage): Future[String] =
  {
    val message = HipChatMapper.mapNotificationMessage(users, hipchatMessage)
    val url = s"$apiBaseUrl/room/$lunchMatorRoomId/notification"
    val headers = List(
      Http.HeaderNames.CONTENT_TYPE -> Http.MimeTypes.JSON,
      Http.HeaderNames.AUTHORIZATION -> s"Bearer $writeToken"
    )

    Logger.debug(s"Sending message to hipChat URL[$url]")
    restClient.post[JsValue, String](url, headers, Json.toJson(message)).map {
      case Some(result) => result
      case None => ""
    }
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
