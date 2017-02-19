package services

import scala.concurrent.Future

import play.api.db.slick.DatabaseConfigProvider

import com.google.inject.Inject
import models.UserRow
import persistence.repository.Users

class UserService @Inject()(implicit val dbConfigDataProvider: DatabaseConfigProvider) extends Service
{

  def getUserById(userId: Int): Future[UserRow] = usingDB {
    Users.findById(userId)
  }

  def addUser(user: UserRow): Future[Int] = usingDB {
    Users.addNewUser(user)
  }

  def getAllUsers: Future[Seq[UserRow]] = usingDB {
    Users.getAll
  }

  def validateUser(email: String): Future[Boolean] = usingDB {
    Users.isPresent(email)
  }
}
