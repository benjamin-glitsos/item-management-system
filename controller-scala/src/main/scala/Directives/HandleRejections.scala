import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import cats.data.NonEmptyChain
import upickle_bundle.general._

object HandleRejections extends ValidationTrait {
  final def apply(): Directive0 = handleRejections(
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
