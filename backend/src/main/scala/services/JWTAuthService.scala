package services

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directive1, Directives}
import org.json4s._
import pdi.jwt.{JwtAlgorithm, JwtJson4s}
import java.util.concurrent.TimeUnit

trait JWTAuthService extends Directives {

  implicit val formats = DefaultFormats
  private val secretKey = "Your.Super.Secret.Key"
  private val algo = JwtAlgorithm.HS512
  private val tokenExpiryPeriodInDays = 1

  def generateToken(claims: JObject): String = {
    JwtJson4s.encode(claims, secretKey, algo)
  }

  def setClaims(email: String): JObject =
    JObject(
      ("email", JString(email)),
      ("expiredAt" -> JString(
        (System.currentTimeMillis() + TimeUnit.DAYS
          .toMillis(tokenExpiryPeriodInDays)).toString
      ))
    )

  private def getClaims(jwt: String): Map[String, String] = {
    JwtJson4s
      .decodeJson(jwt, secretKey, Seq(algo))
      .toOption
      .map(j => j.extract[Map[String, String]])
      .getOrElse(Map.empty)
  }

  def authenticated: Directive1[Map[String, Any]] =
    optionalHeaderValueByName("Authorization").flatMap {
      case Some(jwt) =>
        getClaims(jwt) match {
          case claims if isTokenExpired(claims) =>
            complete(StatusCodes.Unauthorized -> "Session expired.")
          case claims if claims.nonEmpty =>
            provide(claims)
          case _ => complete(StatusCodes.Unauthorized, "Invalid token")
        }
      case _ => complete(StatusCodes.Unauthorized)
    }

  private def isTokenExpired(jwt_content: Map[String, String]): Boolean =
    return jwt_content
      .get("expiredAt")
      .exists(_.toLong < System.currentTimeMillis())

}