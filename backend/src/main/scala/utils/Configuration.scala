package utils

import com.typesafe.config.ConfigFactory
import scala.util.Properties

trait Configuration {
  protected val config = ConfigFactory.load()

  def envOrElseConfig(name: String): String = {
    Properties.envOrElse(
      name.toUpperCase.replaceAll("""\.""", "_"),
      config.getString(name)
    )
  }

  val databaseUrl = envOrElseConfig("database.url")
  val httpHost = envOrElseConfig("http.host")
  val httpPort = envOrElseConfig("http.port")

  val orderBookBtcUrl = f"${envOrElseConfig("binance.ws")}${envOrElseConfig("binance.coin")}${envOrElseConfig("binance.orderbook")}"
  val priceBtcUrl = f"${envOrElseConfig("binance.ws")}${envOrElseConfig("binance.coin")}${envOrElseConfig("binance.candlestick")}"
}
