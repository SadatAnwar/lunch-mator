package exceptions

class EntityNotFoundException(message: String) extends RuntimeException(s"NOT FOUND: $message") {
}
