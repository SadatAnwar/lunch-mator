package services

import javax.inject.Inject

import mappers.UserMapper
import models.{NewUserDto, UserRow}
import persistence.repository.{UserIdentities, Users}
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global

class AuthenticationService @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Service(dbConfigProvider) {

  def signUp(newUserDto: NewUserDto) = {
    createUser(UserMapper.map(newUserDto)).flatMap {
      user => usingDB(UserIdentities.createNewUser(UserIdentityService.map(newUserDto)))
    }
  }

  def getUserByEmail(email: String) = usingDB {
    UserIdentities.getUserIdentity(email).head
  }

  private def createUser(user: UserRow) = usingDB {
    Users.addNewUser(user)
  }
}
