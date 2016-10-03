package services

import javax.inject.Inject
import models.UserIdentity
import persistence.repository.UserIdentities
import play.api.db.slick.DatabaseConfigProvider

class AuthenticationService @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Service(dbConfigProvider) {

  def signUp(userIdentity: UserIdentity) = usingDB {
    UserIdentities.createNewUser(UserIdentityHelper.map(userIdentity))
  }

  def findCreatedUser(identity: UserIdentity) = usingDB {
    UserIdentities.getUserIdentity(identity.email).head
  }
}
