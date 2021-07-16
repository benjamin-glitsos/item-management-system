import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._
import akka.http.scaladsl.model.HttpEntity
import cats.implicits._
import cats.data.Validated.{Valid, Invalid}

object SessionMiddleware extends StringMixin {
  final def apply(): Directive0[ujson.Value] =
    headerValueByName("Authorization") { authorisationValue =>
      {
        if (isEmpty(SessionsDAO.get(authorisationToken))) {
          reject(AuthorisationFailedRejection())
        }
      }
    }
}
