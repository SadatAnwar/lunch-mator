package services

import javax.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider

import mappers.UserMapper
import models.{NewUserDto, UserIdentityRow}
import persistence.repository.UserIdentities

class AuthenticationService @Inject()(userService: UserService)(implicit val dbConfigDataProvider: DatabaseConfigProvider) extends Service {

  def signUp(newUserDto: NewUserDto): Future[UserIdentityRow] = {
    userService.addUser(UserMapper.map(newUserDto)).flatMap {
      user => usingDB(UserIdentities.createNewUser(UserIdentityService.map(newUserDto)))
    }
  }

  def getUserByEmail(email: String): Future[UserIdentityRow] = usingDB {
    UserIdentities.getUserIdentity(email).head
  }
}
