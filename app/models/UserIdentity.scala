package models

case class UserIdentity(email: String, password: String,  sessionUuid: Option[String])

case class UserIdentityStore(email: String, password: String, salt: String, sessionUuid: String)

case class UserSession(email: String, sessionUuid: String)
