import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import java.time.LocalDateTime

object ExceptionHandlerMiddleware
    extends ErrorMixin
    with UpickleMixin
    with RejectionMixin {
  final def apply(): Directive0 = extractRequest flatMap { request =>
    handleExceptions(
      ExceptionHandler {
        case e: Exception => {
          val error: ServerError = ServerError(
            timestamp = LocalDateTime.now.toString,
            method = request.method.name,
            uri = request.uri.toString,
            cause = getRootCause(e)
          )

          printException(error)

          internalServerErrorRejection()
        }
      }
    )
  }
}
