import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes.{
  InternalServerError => InternalServerErrorStatus
}
import cats.data.NonEmptyChain
import java.time.LocalDateTime

object ExceptionHandlerMiddleware extends ErrorMixin with UpickleMixin {
  final def apply(): Directive0 = extractRequest flatMap { request =>
    {
      val timestamp: String = LocalDateTime.now.toString
      val method: String    = request.method.name
      val uri: String       = request.uri.toString
      val cause: String     = getRootCause(e)

      handleExceptions(
        ExceptionHandler {
          case e: Exception => {
            printError(s"Exception at $timestamp:".toUpperCase)

            printError("* Request:")
            printError(s"$method $uri", isColoured = false)

            printError("* Cause:")
            printError(cause, isColoured = false)

            complete(
              InternalServerErrorStatus,
              SerialisedErrors(
                serialiseErrors(NonEmptyChain(InternalServerError()))
              )
            )
          }
        }
      )
    }
  }
}
