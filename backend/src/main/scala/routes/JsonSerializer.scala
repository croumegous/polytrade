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
    balance: Map[String, Double]
)

case class UserBalance(
    email: String,
    balance: Map[String, Double]
)

case class MarketOrder(
    buying: Boolean,
    asset: String,
    amount: Double
)

trait JsonSerializer extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val createUserRequestFormat = jsonFormat3(CreateUserRequest)
  implicit val loginRequestFormat = jsonFormat2(LoginRequest)
  implicit val tokenResponseFormat = jsonFormat1(TokenResponse)
  implicit val userDataFormat = jsonFormat3(UserData)
  implicit val marketOrderFormat = jsonFormat3(MarketOrder)
  implicit val userBalanceFormat = jsonFormat2(UserBalance)
}
