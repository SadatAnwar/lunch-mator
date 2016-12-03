package models

case class User(id: Int, firstName: String, lastName: String, email: String, active: Boolean)

case class UserRow(id: Option[Int], firstName: String, lastName: String, email: String, active: Boolean)

case class NewUserDto(firstName: String, lastName: String, email: String, password: String)

case class UserIdentity(email: String, password: String)

case class UserIdentityRow(email: String, password: String, salt: String)

case class UserSession(email: String, sessionUuid: String)

case class GoogleUserInformation(iss: String, aud: String, sub: String, hd: String, email: String, name: String, picture: String, given_name: String, family_name: String)
