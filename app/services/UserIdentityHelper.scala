package services

import models.{UserIdentity, UserIdentityStore, UserSession}
import org.mindrot.jbcrypt.BCrypt
import persistence.repository.UserIdentities
import scala.concurrent.ExecutionContext.Implicits.global

object UserIdentityHelper {

  def map(userIdentity: UserIdentity) = {
    val salt = BCrypt.gensalt(12)
    val passwordHash = BCrypt.hashpw(userIdentity.password, salt)
    val sessionUuid = java.util.UUID.randomUUID().toString

    UserIdentityStore(userIdentity.email, passwordHash, salt, sessionUuid)
  }

  def map(userIdentityStore: UserIdentityStore) = {
    UserIdentity(userIdentityStore.email, userIdentityStore.password, Some(userIdentityStore.sessionUuid))
  }

  def validateUserIdentity(requestingUser: UserIdentity, createdUser: UserIdentityStore) = {
    val enteredPassword = requestingUser.password

    if (BCrypt.checkpw(enteredPassword, createdUser.password)) {
      createdUser
    }
    else {
      throw new RuntimeException("Invalid password")
    }
  }

  def validateUserSession(userSession: UserSession, userIdentityStore: UserIdentityStore) = {
    if (userSession.sessionUuid.equals(userIdentityStore.sessionUuid)) {
      true
    }
    else {
      false
    }
  }
}
