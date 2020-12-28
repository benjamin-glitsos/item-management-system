import akka.http.scaladsl.server._
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import scala.concurrent.duration._
import upickle.default._
import cats.Applicative
import cats.implicits._
import cats.data.Validated.{Valid, Invalid}
import cats.data.ValidatedNec
import cats.data.NonEmptyChain
import upickle_bundle.implicits._

object HandleRejections {
  def apply(): Directive0 = handleRejections(
    RejectionHandler
      .newBuilder()
      .handle {
        case MissingCookieRejection(cookieName) =>
          complete(BadRequest, "No cookies, no service!!!")
        case ValidationRejection(serialisedErrors, _) =>
          complete(
            InternalServerError,
            SerialisedErrors(serialisedErrors)
          )
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
  )
}
