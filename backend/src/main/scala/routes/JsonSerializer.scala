package routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

// domain model
case class CreateUserRequest(
    email: String,
    password: String,
    confirmPassword: String
)
case class LoginRequest(
    email: String,
    password: String
)

case class TokenResponse(
    token: String
)

case class UserData(
      token: String,
      email: String,
      position: Map[String, Double]
  )


trait JsonSerializer extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val createUserRequestFormat = jsonFormat3(CreateUserRequest)
  implicit val loginRequestFormat = jsonFormat2(LoginRequest)
  implicit val tokenResponseFormat = jsonFormat1(TokenResponse)
  implicit val UserDataFormat = jsonFormat3(UserData)
}
