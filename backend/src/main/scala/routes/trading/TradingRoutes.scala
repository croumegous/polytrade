package routes.trading

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import services.JWTAuthService
import routes.JsonSerializer
import routes.{MarketOrder}
import scala.util.{Right, Left, Either}
import services.{TradingService}

class TradingRoutes(service: TradingService)
    extends Directives
    with JsonSerializer
    with JWTAuthService {
  val routes: Route =
    pathPrefix("trade") {
      path("market") {
        (post & entity(as[MarketOrder])) { request =>
          authenticated { token =>
            service.marketOrder(request, token) { _ =>
              complete(StatusCodes.OK)
            }

          }
        }
      }
    }

}
