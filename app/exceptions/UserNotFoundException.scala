package exceptions

import models.UserRow

class UserNotFoundException(user: UserRow) extends RuntimeException("Unable to find user: " + user) {
}
