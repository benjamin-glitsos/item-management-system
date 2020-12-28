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
import upickle_bundle.general._

object HandleRejections extends ValidationTrait {
  def apply(): Directive0 = handleRejections(
    RejectionHandler
      .newBuilder()
      .handle {
        case ValidationRejection(se, _) =>
          complete(
            InternalServerError,
            SerialisedErrors(se)
          )
        case AuthorizationFailedRejection =>
          complete(
            Forbidden,
            SerialisedErrors(
              serialiseErrors(NonEmptyChain(AuthorisationFailedError()))
            )
          )
      }
      .handleNotFound {
        complete(
          NotFound,
          SerialisedErrors(serialiseErrors(NonEmptyChain(NotFoundError())))
        )
      }
      .result()
  )
}
