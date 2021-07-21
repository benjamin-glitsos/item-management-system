import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes.{
  InternalServerError => InternalServerErrorStatus
}
import cats.data.NonEmptyChain
import java.time.LocalDateTime

object HandleExceptionsMiddleware extends ErrorMixin with UpickleMixin {
  final def apply(): Directive0 = extractRequest flatMap { request =>
    {
      val timestamp: LocalDateTime = LocalDateTime.now()
      val method: String           = request.method.name
      val uri: String              = request.uri.toString

      handleExceptions(
        ExceptionHandler {
          case e: Exception => {
            printError(s"EXCEPTION at $timestamp:")

            printError("* Summary:")
            printError(e.getMessage, isColoured = false)
            printError(e.getStackTrace.head.toString, isColoured = false)

            printError("* Request:")
            printError(s"$method $uri", isColoured = false)

            printError("* Cause:")
            printError(getRootCause(e), isColoured = false)

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
