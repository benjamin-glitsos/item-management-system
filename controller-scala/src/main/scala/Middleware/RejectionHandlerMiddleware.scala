import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import cats.data.NonEmptyChain

object RejectionHandlerMiddleware extends ErrorMixin with UpickleMixin {
  final def apply(): Directive0 = handleRejections(
    RejectionHandler
      .newBuilder()
      .handleNotFound {
        complete(
          NotFound,
          formatErrors(NonEmptyChain(NotFoundError()))
        )
      }
      .result()
  )
}
