package models

import play.api.libs.json.{Json, OFormat}

object GoogleModels {

  case class GoogleAuthorization(access_token: String, id_token: String, expires_in: Long)

  case class GoogleUserInformation(iss: String, aud: String, sub: String, hd: String, email: String, name: String, picture: String, given_name: String, family_name: String)

  implicit val googleAuthorizationFormat: OFormat[GoogleAuthorization] = Json.format[GoogleAuthorization]
  implicit val googleUserFormat: OFormat[GoogleUserInformation] = Json.format[GoogleUserInformation]
}
