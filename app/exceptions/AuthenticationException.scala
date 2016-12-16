package exceptions

class AuthenticationException(val origin: String = "", val message: String = "") extends RuntimeException(message) {
}
