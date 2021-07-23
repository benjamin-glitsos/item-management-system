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
          val timestamp: String = LocalDateTime.now.toString
          val method: String    = request.method.name
          val uri: String       = request.uri.toString
          val cause: String     = getRootCause(e)

          printException(timestamp, method, uri, cause)

          internalServerErrorRejection()
        }
      }
    )
  }
}
