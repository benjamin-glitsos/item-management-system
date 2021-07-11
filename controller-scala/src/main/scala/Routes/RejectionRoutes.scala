import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import cats.data.NonEmptyChain

object RejectionRoutes extends ErrorMixin with UpickleMixin {
  final def notFound(): Route = complete(
    NotFound,
    SerialisedErrors(
      serialiseErrors(NonEmptyChain(NotFoundError()))
    )
  )

  final def badRequestError(se: String): Route = complete(
    BadRequest,
    SerialisedErrors(se)
  )

  final def internalServerError(se: String): Route = complete(
    InternalServerError,
    SerialisedErrors(se)
  )

  final def authorisationFailed(): Route = complete(
    Forbidden,
    SerialisedErrors(
      serialiseErrors(NonEmptyChain(AuthorisationFailedError()))
    )
  )
}
