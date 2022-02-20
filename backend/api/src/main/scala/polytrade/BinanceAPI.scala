package polytrade

import akka.actor.ActorSystem
import akka.Done
import akka.NotUsed
import akka.http.scaladsl.Http
import akka.stream.scaladsl._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ws._

import scala.concurrent.Future
import com.github.andyglow.websocket._

object BinanceAPI {
  def main(args: Array[String]): Unit = {

    val cli = WebsocketClient[String]("wss://stream.binance.com:9443/ws/btcusdt@depth20") { case str =>
      println(str)
    }

    val ws = cli.open()
  }
}
