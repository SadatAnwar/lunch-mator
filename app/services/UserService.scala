package services

import com.google.inject.Inject
import mappers.UserMapper
import models.UserRow
import persistence.repository.Users
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global

class UserService @Inject()(dbConfigDataProvider: DatabaseConfigProvider) extends Service(dbConfigDataProvider) {

  def getUserById(userId: Int) = usingDB {
    Users.findById(userId)
  }

  def addUser(user: UserRow) = usingDB {
    Users.addNewUser(user)
  }

  def getAllUsers = usingDB {
    Users.getAll
  }

  def getUserByEmail(email: String) = usingDB {
    Users.getByEmail(email)
  }.map(user => UserMapper.map(user))
}
