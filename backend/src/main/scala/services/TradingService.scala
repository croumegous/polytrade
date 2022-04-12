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
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directive1, Directives}
import routes.{
  JsonSerializer,
  CreateUserRequest,
  LoginRequest,
  UserData,
  MarketOrder,
  UserBalance
}
import authentication.JWTAuthService
import scala.concurrent.Await
import db.UserRepository.{getUserByEmail, updateUser}
import db.User

class TradingService extends JWTAuthService with JsonSerializer {

  val availableAssets = List[String]("btc")

  def marketOrder(
      order: MarketOrder,
      token: Map[String, Any]
  ): Directive1[Map[String, Any]] = {
    var user: User = getUserByEmail(token("email").toString) match {
      case Some(user) => user
      case None => {
        complete(StatusCodes.NotFound, "User not found")
        User("", "", Map())
      }
    }
    availableAssets.contains(order.asset) match {
      case true =>
        order.buying match {
          case true =>
            user.balance.get("usd") match {
              case Some(usdBalance) =>
                val priceInUsd =
                  order.amount * 41000 // use getBtcPrice function to get real price
                if (usdBalance >= priceInUsd) {
                  val newUsdBalance = usdBalance - priceInUsd
                  var newAssetBalance: Double =
                    user.balance.get(order.asset).getOrElse(0.0) + order.amount
                  val newUser = user.copy(
                    balance =
                      user.balance + ("usd" -> newUsdBalance, order.asset -> newAssetBalance)
                  )
                  updateUser(newUser)
                  complete(
                    StatusCodes.OK,
                    UserBalance(newUser.email, newUser.balance)
                  )
                } else {
                  complete(StatusCodes.BadRequest, "Insufficient funds")
                }
              case None =>
                complete(StatusCodes.BadRequest, "Insufficient funds")
            }
          case false =>
            user.balance.get(order.asset) match {
              case Some(assetBalance) =>
                if (assetBalance >= order.amount) {
                  val priceInUsd: Double =
                    order.amount * 41000 // use getBtcPrice function to get real price
                  val newUsdBalance =
                    user.balance.get("usd").getOrElse(0.0) + priceInUsd
                  val newAssetBalance = assetBalance - order.amount
                  val newUser = user.copy(
                    balance =
                      user.balance + ("usd" -> newUsdBalance, order.asset -> newAssetBalance)
                  )
                  updateUser(newUser)
                  complete(
                    StatusCodes.OK,
                    UserBalance(newUser.email, newUser.balance)
                  )
                } else {
                  complete(StatusCodes.BadRequest, "Insufficient funds")
                }
              case None =>
                complete(StatusCodes.BadRequest, "Insufficient funds")
            }
        }
      case false =>
        complete(StatusCodes.BadRequest, "Asset not available")
    }
  }

}
