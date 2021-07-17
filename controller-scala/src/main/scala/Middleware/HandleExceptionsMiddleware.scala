import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._

object HandleExceptionsMiddleware {
  final def apply(): Directive0 = extractRequest flatMap { request =>
    {
      AnsiConsole.systemInstall();

      var method: String = request.method.name
      var uri: String    = request.uri.toString

      println(
        ansi()
          .a("\n")
          .fg(RED)
          .a(s"Exception thrown from $method $uri")
          .reset()
          .a("\n")
      )

      handleExceptions(
        ExceptionHandler {
          case e: Exception => {
            println(e.getMessage)
            println(e.getCause)
            println(e.getStackTrace.mkString("\n"))

            complete(
              InternalServerError,
              "A server error has occurred."
            )
          }
        }
      )
    }
  }
}
