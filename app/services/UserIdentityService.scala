package services

import models.{UserIdentity, UserIdentityStore, UserSession}
import org.mindrot.jbcrypt.BCrypt

object UserIdentityService {

  def map(userIdentity: UserIdentity) = {
    val salt = BCrypt.gensalt(12)
    val passwordHash = BCrypt.hashpw(userIdentity.password, salt)
    val sessionUuid = java.util.UUID.randomUUID().toString

    UserIdentityStore(userIdentity.email, passwordHash, salt, sessionUuid)
  }

  def map(userIdentityStore: UserIdentityStore) = {
    UserIdentity(userIdentityStore.email, userIdentityStore.password, Some(userIdentityStore.sessionUuid))
  }

  def validatePassword(unhashedPassword: String, hashedPassword: String): Either[String, String] = {
    if (BCrypt.checkpw(unhashedPassword, hashedPassword)) {
      Left("success")
    }
    else {
      Right("error")
    }
  }

  def validateUserSession(userSession: UserSession, userIdentityStore: UserIdentityStore) =
    userSession.sessionUuid.equals(userIdentityStore.sessionUuid)
}
