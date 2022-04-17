package routes.authentication

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives
import services.UserManagementService
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import services.JWTAuthService
import routes.JsonSerializer
import routes.{CreateUserRequest, LoginRequest, TokenResponse, UserData}
import scala.util.{Failure, Try, Success, Right, Left, Either}

class UserManagementRoutes(service: UserManagementService)
    extends Directives
    with JsonSerializer
    with JWTAuthService {
  val routes: Route =
    pathPrefix("user") {
      path("login") {
        (post & entity(as[LoginRequest])) { loginRequest =>
          service.userLogin(loginRequest) match {
            case Right(res) =>
              complete((StatusCodes.OK, res))
            case Left(msg) => complete(StatusCodes.Unauthorized, msg)
          }
        }
      } ~
        path("register") {
          (post & entity(as[CreateUserRequest])) { createUserRequest =>
            service.registerUser(createUserRequest) match {
              case Right(res) =>
                complete((StatusCodes.OK, res))
              case Left(msg) => complete(StatusCodes.Unauthorized, msg)
            }
          }
        }
    }
}
