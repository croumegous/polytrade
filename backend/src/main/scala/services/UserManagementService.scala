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
// import db.mongoUri
import routes.{CreateUserRequest, LoginRequest, UserData}
import authentication.JWTAuthService
import scala.concurrent.Await

class UserManagementService extends JWTAuthService {
  val driver = AsyncDriver()
  val mongoUri: String = scala.util.Properties
    .envOrElse("DATABASE_URL", "mongodb://localhost:27017/polytrade")
  import ExecutionContext.Implicits.global
  val parsedUri = MongoConnection.fromString(mongoUri)
  val futureConnection = parsedUri.flatMap(driver.connect(_))
  def db: Future[DB] = futureConnection.flatMap(_.database("polytrade"))
  def userCollection = db.map(_.collection("user"))

  implicit def userWriter: BSONDocumentWriter[User] = Macros.writer[User]
  implicit def personReader: BSONDocumentReader[User] = Macros.reader[User]

  def getUserByEmail(email: String): Option[User] = {
    Await.result(
      userCollection.flatMap(_.find(document("email" -> email)).one[User]),
      Duration.Inf
    )
  }

  def checkDuplicateUserByEmail(email: String): Boolean = {
    var user = Await.result(
      userCollection.flatMap(_.find(document("email" -> email)).one[User]),
      Duration.Inf
    )
    if (user.isDefined) {
      true
    } else {
      false
    }
  }

  def userLogin(loginRequest: LoginRequest): Either[String, UserData] = {
    var user = getUserByEmail(loginRequest.email).getOrElse(
      return Left("User not found")
    )
    if (loginRequest.password.isBcryptedBounded(user.password)) {
      return Right(
        UserData(
          generateToken(setClaims(user.email)),
          user.email,
          user.position
        )
      )
    } else {
      return Left("Bad credentials")
    }
  }

  def createUser(
      createUserRequest: CreateUserRequest
  ): Either[String, String] = {
    if (checkDuplicateUserByEmail(createUserRequest.email))
      return Left("User already exists")

    var initialPosition = Map[String, Double]("btc" -> 0.0, "usd" -> 10000)
    val user =
      User(
        createUserRequest.email,
        createUserRequest.password.bcryptBounded(generateSalt),
        initialPosition
      )
    userCollection.flatMap(_.insert.one(user).map(_ => {}))
    return Right(generateToken(setClaims(createUserRequest.email)))

  }

  def protectedContent: String = "This method is secured" //temporary to test JWT

  case class User(
      email: String,
      password: String,
      position: Map[String, Double]
  )

  

}
