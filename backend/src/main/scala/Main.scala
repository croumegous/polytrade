// Required import for scala http server
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.event.{Logging, LoggingAdapter}

// Akka http server
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._ // to create http roles

import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

// utils
import scala.util.Failure
import scala.util.Success

// import our package
import routes.binance.Binance
import routes.authentication.UserManagementRoutes
import routes.trading.TradingRoutes
import services.{UserManagementService, TradingService}
import services.WebsocketClient
import utils.Configuration
import services.BinanceFlow

// @main def httpserver: Unit =
object HttpApi extends App with Configuration {

  implicit val actorSystem = ActorSystem(Behaviors.empty, "akka-http")

  val userMananagementRoutes = new UserManagementRoutes(
    new UserManagementService
  )
  val tradingRoutes = new TradingRoutes(new TradingService)

  /** Define routes to listen
    */
  val routes = cors() {
    userMananagementRoutes.routes ~
      tradingRoutes.routes ~
      Binance.routes
  }

  /** Start the server binding routes to listen
    */

  WebsocketClient.start(priceBtcUrl, BinanceFlow.priceSink)
  WebsocketClient.start(orderBookBtcUrl, BinanceFlow.orderbookSink)
  val server = Http().newServerAt(httpHost, httpPort.toInt).bind(routes)
}
