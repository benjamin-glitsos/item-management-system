import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import cats.data.NonEmptyChain
import upickle_bundle.general._

object RejectionRoutes extends ValidationTrait {
  final def notFound(): Route = complete(
    NotFound,
    SerialisedErrors(serialiseErrors(NonEmptyChain(NotFoundError())))
  )

  final def internalServerError(se: String): Route = complete(
    InternalServerError,
    SerialisedErrors(se)
  )

  final def badRequestError(se: String): Route = complete(
    BadRequest,
    SerialisedErrors(se)
  )

  final def authorisationFailed(): Route = complete(
    Forbidden,
    SerialisedErrors(
      serialiseErrors(NonEmptyChain(AuthorisationFailedError()))
    )
  )
}
