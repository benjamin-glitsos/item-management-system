import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes.{
  InternalServerError => InternalServerErrorStatus
}
import cats.data.NonEmptyChain

object HandleExceptionsMiddleware extends ErrorMixin with UpickleMixin {
  final def apply(): Directive0 = extractRequest flatMap { request =>
    {
      var method: String = request.method.name
      var uri: String    = request.uri.toString

      printError(s"Exception thrown from $method $uri")

      handleExceptions(
        ExceptionHandler {
          case e: Exception => {
            printError(e.getMessage)
            printError(e.getCause)
            printError(e.getStackTrace.mkString("\n"))

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
