package services

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
import models.{HipChatMessage, HipChatUser, Page}

class HipChatService @Inject()(configuration: Configuration, restClient: RESTClientWrapper, userService: UserService) {

  private val apiBaseUrl = configuration.getString("hipchat.api.baseurl")
  private val readToken = configuration.getString("hipchat.api.read.accesstoken")
  private val writeToken = configuration.getString("hipchat.lunchmator.write.accesstoken")
  private val lunchMatorRoomId = configuration.getString("hipchat.lunchmator.chatroom.id")

  def getUsersWithNameIn(name: String): Future[Seq[HipChatUser]] = {

    findUser(name)
  }

  def sendMessage(users: List[HipChatUser], hipChatMessage: HipChatMessage) = {
    val message = HipChatMapper.mapNotificationMessage(users, hipChatMessage)
    val url = s"${apiBaseUrl}room/$lunchMatorRoomId/notification"
    Logger.info(url)
    val headers = List(
      Http.HeaderNames.CONTENT_TYPE -> Http.MimeTypes.JSON,
      Http.HeaderNames.AUTHORIZATION -> s"Bearer $writeToken"
    )

    restClient.post[JsValue, String](url, headers, Json.toJson(message))
  }

  private def findUser(name: String, resultSetSize: Int = 200): Future[Seq[HipChatUser]] = {
    val url = s"${apiBaseUrl}user?max-results=$resultSetSize"
    val headers = List(Http.HeaderNames.AUTHORIZATION -> s"Bearer $readToken")

    restClient.get[Page[HipChatUser]](url, headers).map { result =>
      PageMapper.map(result)
        .filter(u => userNameLike(u, name))
        .toList
    }
  }

  private def userNameLike(user: HipChatUser, string: String) = {
    user.name.toLowerCase.contains(string.toLowerCase) || user.mention_name.toLowerCase.contains(string.toLowerCase)
  }
}
