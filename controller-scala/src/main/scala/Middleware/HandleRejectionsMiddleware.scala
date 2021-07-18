import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import cats.data.NonEmptyChain

object HandleRejectionsMiddleware extends ErrorMixin with UpickleMixin {
  final def apply(): Directive0 = handleRejections(
    RejectionHandler
      .newBuilder()
      .handle {
        case ValidationRejection(se: String, _) =>
          complete(
            BadRequest,
            SerialisedErrors(se)
          )
        case MissingHeaderRejection(headerName: String) =>
          complete(
            InternalServerError,
            headerName
          )
      }
      .handleNotFound {
        complete(
          NotFound,
          SerialisedErrors(
            serialiseErrors(NonEmptyChain(NotFoundError()))
          )
        )
      }
      .result()
  )
}
