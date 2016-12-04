package persistence.repository

import models.UserRow
import slick.dbio.Effect.Write
import slick.driver.PostgresDriver.api._
import slick.profile.FixedSqlAction

class Users(tag: Tag) extends Table[UserRow](tag, Some("lunch_world"), "users") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def firstName = column[String]("first_name")

  def lastName = column[String]("last_name")

  def email = column[String]("email")

  def active = column[Boolean]("active")

  override def * = (id.?, firstName, lastName, email, active) <> (UserRow.tupled, UserRow.unapply _)
}

object Users {

  val users = TableQuery[Users]

  def getByEmail(email: String) = {
    users.filter(_.email === email).result.head
  }

  def findById(userId: Int) = {
    users.filter(_.id === userId).result.head
  }

  def findByName(name: String) = {
    users.filter(_.firstName === name).result
  }

  def addNewUser(user: UserRow): FixedSqlAction[Int, NoStream, Write] = {
    users += user
  }

  def getAll = {
    users.result
  }
}
