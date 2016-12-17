package persistence.repository

import scala.concurrent.ExecutionContext.Implicits.global

import models.UserRow
import slick.dbio.DBIOAction
import slick.dbio.Effect.{Read, Write}
import slick.driver.PostgresDriver.api._
import slick.profile.{FixedSqlAction, FixedSqlStreamingAction, SqlAction}

class Users(tag: Tag) extends Table[UserRow](tag, Some("lunch_world"), "users") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def firstName = column[String]("first_name")

  def lastName = column[String]("last_name")

  def email = column[String]("email")

  def lastLogin = column[String]("last_login")

  def picture = column[String]("picture_link")

  def active = column[Boolean]("active")

  override def * = (id.?, firstName, lastName, email, active) <> (UserRow.tupled, UserRow.unapply)
}

object Users {

  val users = TableQuery[Users]

  def getByEmail(email: String): SqlAction[UserRow, NoStream, Read] = {
    users.filter(_.email === email).result.head
  }

  def isPresent(email: String): FixedSqlAction[Boolean, _root_.slick.driver.PostgresDriver.api.NoStream, Read] = {
    users.filter(_.email === email).exists.result
  }

  def findById(userId: Int): SqlAction[UserRow, NoStream, Read] = {
    users.filter(_.id === userId).result.head
  }

  def findByName(name: String): FixedSqlStreamingAction[Seq[UserRow], UserRow, Read] = {
    users.filter(_.firstName === name).result
  }

  def addNewUser(user: UserRow): DBIOAction[Int, NoStream, Read with Write] = {
    users.filter(_.email === user.email).exists.result.flatMap { exists =>
      if (!exists) {
        users += user
      } else {
        //TODO: Update data here
        DBIO.successful(0)
      }
    }
  }

  def getAll: FixedSqlStreamingAction[Seq[UserRow], UserRow, Read] = {
    users.result
  }
}
