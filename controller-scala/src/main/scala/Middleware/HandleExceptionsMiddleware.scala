import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes.{
  InternalServerError => InternalServerErrorStatus
}
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._
import cats.data.NonEmptyChain

object HandleExceptionsMiddleware extends ErrorMixin with UpickleMixin {
  final def apply(): Directive0 = extractRequest flatMap { request =>
    {
      AnsiConsole.systemInstall();

      var method: String = request.method.name
      var uri: String    = request.uri.toString

      System.err.println(
        ansi()
          .fg(RED)
          .a(s"Exception thrown from $method $uri")
          .a("\n")
          .reset()
      )

      handleExceptions(
        ExceptionHandler {
          case e: Exception => {
            System.err.println(e.getMessage)
            System.err.println(e.getCause)
            System.err.println(e.getStackTrace.mkString("\n"))

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
