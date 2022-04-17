package routes.binance

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.actor.typed.scaladsl.AskPattern._

import scala.concurrent.Future
import akka.stream.scaladsl._
import akka.actor._
import akka.http.scaladsl.model.ws._
import akka.NotUsed
import akka.stream.{OverflowStrategy, ActorMaterializer}

import services.BinanceFlow
object Binance {

  val candleStickFlow: Flow[Message, Message, NotUsed] =
    Flow[Message]
      .mapAsync(1) {
        case TextMessage.Strict(text) =>
          Future.successful(text)
        case _ => Future.failed(new Exception("Unexpected message"))
      }
      .via(Flow.fromSinkAndSource(Sink.ignore, BinanceFlow.priceSource))
      .map[Message](string => TextMessage(string))

  val orderBookFlow: Flow[Message, Message, NotUsed] =
    Flow[Message]
      .mapAsync(1) {
        case TextMessage.Strict(text) => Future.successful(text)
        case _ => Future.failed(new Exception("Unexpected message"))
      }
      .via(Flow.fromSinkAndSource(Sink.ignore, BinanceFlow.orderbookSource))
      .map[Message](string => TextMessage(string))

  val routes: Route =
    pathPrefix("ws") {
      path("candlestick") {
        get {
          handleWebSocketMessages(candleStickFlow)
        }
      } ~
        path("orderbook") {
          get {
            handleWebSocketMessages(orderBookFlow)
          }
        }
    }
}
