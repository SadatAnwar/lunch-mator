package services

import java.text.Normalizer
import java.util.regex.Pattern

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.Configuration
import play.api.Logger
import play.api.libs.json.{JsValue, Json}
import play.mvc.Http

import client.RESTClientWrapper
import com.google.inject.Inject
import mappers.{HipChatMapper, PageMapper}
import models.Formats._
import models._
import org.apache.commons.compress.utils.CharsetNames

class HipChatService @Inject()(configuration: Configuration, restClient: RESTClientWrapper, userService: UserService) {

  private val apiBaseUrl = configuration.getString("hipchat.api.baseurl")
  private val readToken = configuration.getString("hipchat.api.read.accesstoken")
  private val writeToken = configuration.getString("hipchat.lunchmator.write.accesstoken")
  private val lunchMatorRoomId = configuration.getString("hipchat.lunchmator.chatroom.id")

  def getUsersWithNameIn(name: String): Future[Seq[HipChatUser]] = {

    findUser(name)
  }

  def sendInvitation(invitation: InvitationDto): Future[String] = {
    val invitationMessage = s"Hello! You have been invited for lunch at ${invitation.lunch.restaurant.name}. " +
      s"To join, click http://lunch-mator.rebuy.de/lunch/${invitation.lunch.id}"

    sendMessage(invitation.users, HipChatMessage(invitationMessage))
  }

  def sendMessage(hipChatMessageDto: HipChatMessageDto): Future[String] = {

    sendMessage(hipChatMessageDto.users, hipChatMessageDto.message)
  }

  private def sendMessage(users: Seq[HipChatUser], hipchatMessage: HipChatMessage): Future[String] = {
    val message = HipChatMapper.mapNotificationMessage(users, hipchatMessage)
    val url = s"${apiBaseUrl}room/$lunchMatorRoomId/notification"
    Logger.info(url)
    val headers = List(
      Http.HeaderNames.CONTENT_TYPE -> Http.MimeTypes.JSON,
      Http.HeaderNames.AUTHORIZATION -> s"Bearer $writeToken"
    )

    restClient.post[JsValue, String](url, headers, Json.toJson(message)).map {
      case Some(result) => result
      case None => ""
    }
  }

  private def findUser(name: String, resultSetSize: Int = 200): Future[Seq[HipChatUser]] = {
    val url = s"${apiBaseUrl}user?max-results=$resultSetSize"
    val headers = List(
      Http.HeaderNames.AUTHORIZATION -> s"Bearer $readToken",
      Http.HeaderNames.ACCEPT_CHARSET -> CharsetNames.UTF_8
    )

    restClient.get[Page[HipChatUser]](url, headers).map {
      case Some(result) => PageMapper.map(result)
        .filter(u => userNameLike(u, name))
        .toList
      case None => List()
    }
  }

  private def userNameLike(user: HipChatUser, searchString: String) = {
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    val userName = pattern.matcher(Normalizer.normalize(user.name, Normalizer.Form.NFD).toLowerCase).replaceAll("")
    val mentionName = pattern.matcher(Normalizer.normalize(user.mention_name, Normalizer.Form.NFD).toLowerCase).replaceAll("")

    userName.contains(searchString.toLowerCase) || mentionName.contains(searchString.toLowerCase)
  }
}
