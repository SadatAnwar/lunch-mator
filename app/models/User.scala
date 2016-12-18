package models

import org.joda.time.DateTime

case class User(id: Int, firstName: String, lastName: String, email: String, active: Boolean)

case class UserRow(id: Option[Int], firstName: String, lastName: String, email: String, picture: String, lastLogin: DateTime, active: Boolean)

case class OAuthUserRow(email: String, accessToken: String, idToken: String)

case class NewUserDto(firstName: String, lastName: String, email: String, password: String)

case class UserIdentity(email: String, password: String)

case class UserIdentityRow(email: String, password: String, salt: String)

case class UserSession(email: String, sessionUuid: String)
