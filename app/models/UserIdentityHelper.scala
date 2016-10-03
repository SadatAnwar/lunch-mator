package models

import org.mindrot.jbcrypt.BCrypt

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

  def validate(requestingUser: UserIdentity, createdUser: UserIdentityStore) = {
    val enteredPassword = requestingUser.password

    if (BCrypt.checkpw(enteredPassword, createdUser.password))
      createdUser
    else
      throw new RuntimeException("Invalid password")
  }
}
