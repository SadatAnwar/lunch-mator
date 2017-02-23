package persistence.repository

import com.github.tototoshi.slick.PostgresJodaSupport._
import models.ChatMessage
import org.joda.time.DateTime
import slick.dbio.Effect.{Read, Write}
import slick.driver.PostgresDriver.api._
import slick.profile.{FixedSqlAction, FixedSqlStreamingAction}

class ChatMessages(tag: Tag) extends Table[ChatMessage](tag, Some("lunch_world"), "lunch_tables")
{

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def lunchId = column[Int]("lunch_id")

  def authorId = column[Int]("author_id")

  def replyTo = column[Int]("reply_to")

  def createdAt = column[DateTime]("created_at")

  def message = column[String]("message")

  override def * = (id.?, lunchId, authorId, replyTo, createdAt, message) <> (ChatMessage.tupled, ChatMessage.unapply _)
}

object ChatMessages
{
  lazy private val chatMessages = TableQuery[ChatMessages]

  def getMessagesForLunch(lunchId: Int): FixedSqlStreamingAction[Seq[ChatMessage], ChatMessage, Read] =
  {
    chatMessages.filter(_.lunchId === lunchId).result
  }

  def postNewMessage(message: ChatMessage): FixedSqlAction[Int, NoStream, Write] =
  {
    chatMessages += message
  }
}
