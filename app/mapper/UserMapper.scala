package mapper

import models.{NewUserDto, User}

object UserMapper {

  def map(newUserDto: NewUserDto): User = {
    User(None, newUserDto.firstName, newUserDto.lastName, newUserDto.email, true)
  }
}
