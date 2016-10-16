package mappers

import models.{NewUserDto, User, UserRow}

object UserMapper {

  def map(user: UserRow): User = {
    User(user.id.getOrElse(-1), user.firstName, user.lastName, user.email, user.active)
  }

  def map(newUserDto: NewUserDto): UserRow = {
    UserRow(None, newUserDto.firstName, newUserDto.lastName, newUserDto.email, true)
  }
}
