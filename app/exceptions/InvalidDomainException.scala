package exceptions

case class InvalidDomainException(email: String) extends RuntimeException(s"Invalid domain for $email") {
}
