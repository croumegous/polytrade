package services

import akka.actor._
import akka.http.scaladsl._
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.stream._
import akka.stream.scaladsl._

object BinanceFlow {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val (priceSink, priceSource) =
    MergeHub.source[String].toMat(BroadcastHub.sink[String])(Keep.both).run()

  val (orderbookSink, orderbookSource) =
    MergeHub.source[String].toMat(BroadcastHub.sink[String])(Keep.both).run()
}
