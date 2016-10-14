package persistence.repository

import models.User
import slick.driver.PostgresDriver.api._

class Users(tag: Tag) extends Table[User](tag, Some("lunch_world"), "users") {

  def id = column[Int]("id", O.AutoInc, O.PrimaryKey)

  def firstName = column[String]("first_name")

  def lastName = column[String]("last_name")

  def email = column[String]("email")

  def active = column[Boolean]("active")

  override def * = (id.?, firstName, lastName, email, active) <> (User.tupled, User.unapply _)
}

object Users {

  val users = TableQuery[Users]

  def findById(userId: Int) = {
    users.filter(_.id === userId).result.head
  }

  def findByName(name: String) = {
    users.filter(_.firstName === name).result
  }

  def addNewUser(user: User) = {
    users += user
  }

  def getAll = {
    users.result
  }
}
