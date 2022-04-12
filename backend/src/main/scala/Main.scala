// Required import for scala http server
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

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


// @main def httpserver: Unit =
object HttpApi {
  def main(args: Array[String]): Unit = {

    implicit val actorSystem = ActorSystem(Behaviors.empty, "akka-http")

    val userMananagementRoutes = new UserManagementRoutes(new UserManagementService)
    val tradingRoutes = new TradingRoutes(new TradingService)

    /** Define routes to listen
      */
    val routes = cors() {
      userMananagementRoutes.routes ~
      tradingRoutes.routes ~
      Binance.routes ~
      path("") {
        get {
          complete(
            HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Root path</h1>")
            )
          }
        }
      }

    /** Start the server binding routes to listen
      */
    val server = Http().newServerAt("127.0.0.1", 8080).bind(routes)
    // TODO Add execution context to handle failure or success
    // server.onComplete {
    //   case Success(binding) =>
    //     val address = binding.localAddress
    //     system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
    //   case Failure(ex) =>
    //     system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
    //     system.terminate()
    // }
  }
}
