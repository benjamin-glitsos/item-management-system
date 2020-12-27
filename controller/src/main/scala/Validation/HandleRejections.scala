import akka.http.scaladsl.server._
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._
import akka.http.scaladsl.model.HttpEntity
import upickle.default._
import cats.Applicative
import cats.implicits._
import cats.data.Validated.{Valid, Invalid}
import cats.data.ValidatedNec

object HandleRejections {
  private val rejectionHandlers = RejectionHandler
    .newBuilder()
    .handle {
      case MissingCookieRejection(cookieName) =>
        complete(HttpResponse(BadRequest, entity = "No cookies, no service!!!"))
      case AuthorizationFailedRejection =>
        complete(Forbidden, "You're out of your depth!")
      case ValidationRejection(msg, _) =>
        complete(InternalServerError, "That wasn't valid! " + msg)
    }
    .handleAll[MethodRejection] { methodRejections =>
      val names = methodRejections.map(_.supported.name)
      complete(
        MethodNotAllowed,
        s"Can't do that! Supported: ${names mkString " or "}!"
      )
    }
    .handleNotFound { complete((NotFound, "Not here!")) }
    .result()

  def apply(): Directive0 = handleRejections(rejectionHandlers)
}
