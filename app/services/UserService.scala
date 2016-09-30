package services

import com.google.inject.Inject
import models.User
import persistence.repository.Users
import play.api.db.slick.DatabaseConfigProvider

class UserService @Inject()(dbConfigDataProvider: DatabaseConfigProvider) extends Service(dbConfigDataProvider) {

  def addUser(user: User) = usingDB {
    Users.saveUser(user)
  }

  def getAllUsers = usingDB {
    Users.getAll
  }
}
