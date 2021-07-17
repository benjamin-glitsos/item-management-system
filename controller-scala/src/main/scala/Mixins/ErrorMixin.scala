import org.typelevel.ci.{CIString => CaseInsensitive}
import cats.data.NonEmptyChain
import upickle.default._
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._

trait ErrorMixin extends UpickleMixin {
  AnsiConsole.systemInstall();

  final val INTERNAL_SERVER_ERROR = CaseInsensitive("internal_server_error")
  final val NOT_FOUND             = CaseInsensitive("not_found")
  final val INVALID_INPUT         = CaseInsensitive("invalid_input")

  final def printError(message: String): Unit = System.err.println(
    ansi()
      .fg(RED)
      .a(message)
      .a("\n")
      .reset()
  )

  final def serialiseErrors(errors: NonEmptyChain[Error]): String = {
    write(
      errors.toChain.toList
    )
  }
}
