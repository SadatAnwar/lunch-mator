package mappers

import exceptions.UserNotFoundException
import models.GoogleModels.{GoogleAuthorization, GoogleUserInformation}
import models.{NewUserDto, OAuthUserRow, User, UserRow}

object UserMapper {

  def map(user: UserRow): User = {
    User(user.id.getOrElse(throw new UserNotFoundException(user)), user.firstName, user.lastName, user.email, user.active)
  }

  def map(newUserDto: NewUserDto): UserRow = {
    UserRow(None, newUserDto.firstName, newUserDto.lastName, newUserDto.email, active = true)
  }

  def map(googleUserInformation: GoogleUserInformation): UserRow = {
    UserRow(None, googleUserInformation.given_name, googleUserInformation.family_name, googleUserInformation.email, active = true)
  }

  def map(googleUserInformation: GoogleUserInformation, googleAuthorization: GoogleAuthorization): OAuthUserRow = {
    OAuthUserRow(googleUserInformation.email, googleAuthorization.access_token, googleAuthorization.id_token)
  }
}
