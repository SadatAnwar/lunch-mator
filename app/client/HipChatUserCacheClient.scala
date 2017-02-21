package client

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

import play.Configuration
import play.api.Logger
import play.api.cache.CacheApi
import play.cache.NamedCache
import play.mvc.Http

import com.google.inject.Inject
import models.Formats._
import models.{HipChatUser, Page}
import org.apache.commons.compress.utils.CharsetNames

class HipChatUserCacheClient @Inject()(configuration: Configuration,
                                       @NamedCache("hipchat-user-cache") cache: CacheApi,
                                       restClientWrapper: RestClientWrapper)
                                      (implicit executionContext: ExecutionContext)
{

  private val apiBaseUrl = configuration.getString("hipchat.api.baseurl")
  private val readToken = configuration.getString("hipchat.api.read.accesstoken")

  def getAllUsers: Future[Seq[HipChatUser]] =
  {
    val url = s"$apiBaseUrl/user?max-results=50"
    cache.getOrElse("hipchat-users", 12.hours) {
      getUsers(url)
    }
  }

  private def getUsers(link: String): Future[Seq[HipChatUser]] =
  {
    val headers = List(
      Http.HeaderNames.AUTHORIZATION -> s"Bearer $readToken",
      Http.HeaderNames.ACCEPT_CHARSET -> CharsetNames.UTF_8
    )
    Logger.info(s"Requesting HipChat for userList | URL:[$link]")
    restClientWrapper.get[Page[HipChatUser]](link, headers).flatMap { page =>
      if (page.links.next.isEmpty) {
        Future.successful(page.items)
      } else {
        getUsers(page.links.next.get).map { users =>
          users ++ page.items
        }
      }
    }
  }
}
