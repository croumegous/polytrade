package services

import scala.concurrent.{ExecutionContext, Future}
import reactivemongo.api.{Cursor, DB, MongoConnection, AsyncDriver}
import reactivemongo.api.bson.{
  BSONDocumentWriter,
  BSONDocumentReader,
  Macros,
  document
}
import scala.util.{Failure, Try, Success, Right, Left, Either}
import com.github.t3hnar.bcrypt._
import scala.concurrent._
import scala.concurrent.duration._
import routes.{CreateUserRequest, LoginRequest, UserData}
import scala.concurrent.Await
import db.UserRepository.{getUserByEmail, checkDuplicateUserByEmail, createUser}
import db.User

class UserManagementService extends JWTAuthService {

  def userLogin(loginRequest: LoginRequest): Either[String, UserData] = {
    
    var user = getUserByEmail(loginRequest.email).getOrElse(
      return Left("User not found")
    )
    if (loginRequest.password.isBcryptedBounded(user.password)) {
      return Right(
        UserData(
          generateToken(setClaims(user.email)),
          user.email,
          user.balance
        )
      )
    } else {
      return Left("Bad credentials")
    }
  }

  def registerUser(
      createUserRequest: CreateUserRequest
  ): Either[String, UserData] = {
    if(!isValidEmail(createUserRequest.email)) return Left("Invalid email format")
    if (checkDuplicateUserByEmail(createUserRequest.email))
      return Left("User already exists")

    var initialBalance = Map[String, Double]("btc" -> 0.0, "usd" -> 10000)
    val user =
      User(
        createUserRequest.email,
        createUserRequest.password.bcryptBounded(generateSalt),
        initialBalance
      )
    createUser(user)
    return Right(
      UserData(
        generateToken(setClaims(user.email)),
        user.email,
        user.balance
      )
    )

  }

  def isValidEmail(email: String): Boolean =
  """(\w+)@([\w\.]+)""".r.unapplySeq(email).isDefined

}
