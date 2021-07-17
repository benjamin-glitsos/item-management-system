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
            printErrorHeading(timestamp, "Exception Request:")
            printError(s"$method $uri", isColoured = false)

            printErrorHeading(timestamp, "Exception Message:")
            printError(e.getMessage, isColoured = false)

            // printErrorHeading(timestamp, "Exception Cause:")
            // printError(e.getCause.toString, isColoured = false)

            printErrorHeading(timestamp, "Exception Stack Trace:")
            printError(e.getStackTrace.mkString("\n"), isColoured = false)

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
