package db

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
import services.JWTAuthService
import scala.concurrent.Await
import utils.Configuration


object UserRepository extends Configuration{
  val driver = AsyncDriver()
  import ExecutionContext.Implicits.global
  val parsedUri = MongoConnection.fromString(databaseUrl)
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

  def createUser(user: User): Unit = {
    userCollection.flatMap(_.insert(user))
  }

  def updateUser(user: User): Unit = {
    userCollection.flatMap(_.update(
      document("email" -> user.email),
      document("$set" -> document("balance" -> user.balance))
    ))
  }
}
