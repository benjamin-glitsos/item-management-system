import org.typelevel.ci.{CIString => CaseInsensitive}
import cats.data.NonEmptyChain
import upickle.default._
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._
import java.time.LocalDateTime
import org.apache.commons.lang3.exception.ExceptionUtils._

trait ErrorMixin extends UpickleMixin with StringMixin {
  AnsiConsole.systemInstall();

  final val INTERNAL_SERVER_ERROR = CaseInsensitive("internal_server_error")
  final val NOT_FOUND             = CaseInsensitive("not_found")
  final val INVALID_INPUT         = CaseInsensitive("invalid_input")

  final def printError(message: String, isColoured: Boolean = true): Unit =
    System.err.println(
      if (isColoured) {
        ansi()
          .fg(RED)
          .a(message)
          .reset()
      } else {
        message
      }
    )

  final def printErrorHeading(timestamp: LocalDateTime, message: String): Unit =
    printError(s"[$timestamp] $message")

  final def printErrorContent(message: String): Unit =
    printError(message, isColoured = false)

  final def serialiseErrors(errors: NonEmptyChain[Error]): String = {
    write(
      errors.toChain.toList
    )
  }

  final def getRootCause(throwable: Throwable): String = {
    val rootCause: String = {
      @scala.annotation.tailrec
      def inner(t: Throwable, v: Vector[Throwable]): Vector[Throwable] = {
        val tail = v :+ t
        if (t.getCause == null) tail else inner(t.getCause, tail)
      }

      val causes: Vector[Throwable] = inner(throwable, Vector[Throwable]())

      causes.map(getStackTrace(_)).mkString("\n")
    }

    if (isEmpty(rootCause)) {
      "None"
    } else {
      rootCause
    }
  }
}
