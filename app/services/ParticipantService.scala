package services

import javax.inject.Inject

import models.{Lunch, User}
import play.api.db.slick.DatabaseConfigProvider

class ParticipantService @Inject()(implicit val dbConfigDataProvider: DatabaseConfigProvider, val userService: UserService, val lunchService: LunchService) extends Service{

  def addUserToLunch(user: User, lunch: Lunch) = {

  }
}
