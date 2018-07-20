package models

case class ValidationResult(errors: List[String]) {

  def isValid: Boolean = {
    errors.isEmpty
  }
}

object ValidationResult {
  def valid: ValidationResult = ValidationResult(List.empty)
}
