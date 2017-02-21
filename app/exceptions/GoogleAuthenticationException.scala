package exceptions

case class GoogleAuthenticationException(override val message: String = "") extends AuthenticationException(message)
