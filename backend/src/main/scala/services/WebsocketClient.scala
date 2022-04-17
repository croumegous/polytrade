package services

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{
  Message,
  TextMessage,
  WebSocketRequest,
  WebSocketUpgradeResponse
}
import akka.stream.{ActorMaterializer, Graph, SinkShape}
import akka.stream.scaladsl.{Flow, Keep, Sink}
import scala.concurrent.Promise
import akka.stream.scaladsl.Source
import scala.concurrent.{Await, Future}

object WebsocketClient {
  def start(url: String, sink: Graph[SinkShape[String], Any]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    import system.dispatcher

    val flow: Flow[Message, Message, NotUsed] =
      Flow[Message]
        .mapAsync(1) { case TextMessage.Strict(text) => Future.successful(text)
            case _ => Future.failed(new Exception("Unexpected websocket message"))
        }
        .via(Flow.fromSinkAndSource(sink, Source.maybe))

    val (upgradeResponse, promise) =
      Http().singleWebSocketRequest(WebSocketRequest(url), flow)

    val connected = upgradeResponse.map { upgrade =>
      if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
        Done
      } else {
        throw new RuntimeException(
          s"Connection failed: ${upgrade.response.status}"
        )
      }
    }
  }
}
