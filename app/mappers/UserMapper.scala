package mappers

import exceptions.UserNotFoundException
import models.{OAuthUserRow, User, UserRow}
import models.GoogleModels.{GoogleAuthorization, GoogleUserInformation}
import org.joda.time.DateTime

object UserMapper {

  def map(user: UserRow): User = {
    User(user.id.getOrElse(throw new UserNotFoundException(user)), user.firstName, user.lastName, user.email, user.active)
  }

  def map(user: GoogleUserInformation): UserRow = {
    UserRow(None, user.given_name, user.family_name, user.email, user.picture.getOrElse(""), DateTime.now(), active = true)
  }

  def map(userInfo: GoogleUserInformation, auth: GoogleAuthorization): OAuthUserRow = {
    OAuthUserRow(userInfo.email, auth.access_token, auth.id_token)
  }
}
