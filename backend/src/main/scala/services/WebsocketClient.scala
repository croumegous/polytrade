package services

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{Message,WebSocketRequest, WebSocketUpgradeResponse}
import akka.stream.{ActorMaterializer}
import akka.stream.scaladsl.{Flow, Keep, Sink}
import scala.concurrent.Promise
import akka.stream.scaladsl.Source


object WebsocketClient{
  def start(url: String): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    import system.dispatcher


    val flow: Flow[Message, Message, Promise[Option[Message]]] =
      Flow.fromSinkAndSourceMat(
        Sink.foreach[Message](record=>println(record)),
        Source.maybe[Message])(Keep.right)


    val (upgradeResponse, promise) =
      Http().singleWebSocketRequest(
        WebSocketRequest(url),
        flow)

    val connected = upgradeResponse.map { upgrade =>
      if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
        Done
      } else {
        throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
      }
    }
    // connected.onComplete(println)
    //promise.success(None)
  }
}

//https://dev-listener.medium.com/learning-to-tame-akka-http-websocket-c4cec2cdf23f
//https://stackoverflow.com/questions/39388655/not-able-to-send-multiple-message-with-same-websocket-connection-using-akka-http

//https://stackoverflow.com/questions/39727729/akka-streams-what-does-mat-represents-in-sourceout-mat
//https://github.com/johanandren/chat-with-akka-http-websockets.git


//https://github.com/babloo80/akka-http-websocket


//https://doc.akka.io/docs/akka-http/current/client-side/websocket-support.html?language=scala#Half-Closed_WebSockets
//https://doc.akka.io/docs/akka-http/current/server-side/websocket-support.html
// https://markatta.com/codemonkey/posts/chat-with-akka-http-websockets-old/