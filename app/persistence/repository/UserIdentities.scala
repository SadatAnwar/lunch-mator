package persistence.repository

import models.UserIdentityStore
import slick.driver.PostgresDriver.api._

class UserIdentities(tag: Tag) extends Table[UserIdentityStore](tag, Some("lunch_world"), "user_identity") {

  def userEmail = column[String]("user_email", O.PrimaryKey)

  def password = column[String]("encrypted_password")

  def salt = column[String]("salt")

  def sessionUuid = column[String]("session_uuid")

  override def * = (userEmail, password, salt, sessionUuid) <> (UserIdentityStore.tupled, UserIdentityStore.unapply _)
}

object UserIdentities {

  val userIdentities = TableQuery[UserIdentities]

  def getUserIdentity(email: String) = {
    userIdentities.filter(_.userEmail === email).result.head
  }

  def createNewUser(userIdentity: UserIdentityStore) = {
    (userIdentities returning userIdentities) += userIdentity
  }
}
