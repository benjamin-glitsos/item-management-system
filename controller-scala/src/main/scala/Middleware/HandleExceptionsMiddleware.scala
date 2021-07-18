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
            printErrorHeading(timestamp, "Exception:".toUpperCase)

            printErrorHeading(timestamp, "* Request:")
            printErrorContent(s"$method $uri")

            printErrorHeading(timestamp, "* Message:")
            printErrorContent(e.getMessage)

            printErrorHeading(timestamp, "* Cause:")
            printErrorContent(getRootCause(e))

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
