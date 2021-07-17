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

  final def serialiseErrors(errors: NonEmptyChain[Error]): String = {
    write(
      errors.toChain.toList
    )
  }

  final def getRootCause(th: Throwable) = { // TODO: add return type
    @scala.annotation.tailrec
    def inner(th: Throwable, v: Vector[Throwable]): Vector[Throwable] = {
      val tail = v :+ th
      if (th.getCause == null) tail else inner(th.getCause, tail)
    }
    inner(th, Vector[Throwable]())
  }

  final def throwableToString(th: Throwable): String = getStackTrace(th)

  final def getRootCauseFormatted(th: Throwable): String = {
    val s: String = getRootCause(th).map(throwableToString(_)).mkString("\n")

    if (isEmpty(s)) {
      "None"
    } else {
      s
    }
  }
}
