package services

import exceptions.PasswordValidationException
import models.{NewUserDto, UserIdentity, UserIdentityRow}
import org.mindrot.jbcrypt.BCrypt

object UserIdentityService {

  def map(newUserDto: NewUserDto): UserIdentityRow = {
    val salt = BCrypt.gensalt(12)
    val passwordHash = BCrypt.hashpw(newUserDto.password, salt)

    UserIdentityRow(newUserDto.email, passwordHash, salt)
  }

  def map(userIdentityRow: UserIdentityRow) = {
    UserIdentity(userIdentityRow.email, userIdentityRow.password)
  }

  def validatePassword(unhashedPassword: String, hashedPassword: String): Either[String, String] = {
    if (BCrypt.checkpw(unhashedPassword, hashedPassword)) {
      Left("success")
    }
    else {
      throw new PasswordValidationException
    }
  }
}
