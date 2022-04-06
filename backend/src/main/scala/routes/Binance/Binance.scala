package routes.binance

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.actor.typed.scaladsl.AskPattern._

import scala.concurrent.Future

object Binance {
  val routes: Route =
  path("binance") {
    get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Binance API there</h1>"))
    }
  }

  // val baseUrl = "wss://stream.binance.com:9443/ws/"

  // def wsClient(url: String): Unit = {

  //   val cli = WebsocketClient[String](f"$baseUrl%s$url%s") { case str =>
  //     println(str)
  //   }

  //   val ws = cli.open()
  // }
}