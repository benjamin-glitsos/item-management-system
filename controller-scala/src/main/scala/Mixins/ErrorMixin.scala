import cats.data.NonEmptyChain
import upickle.default._
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._
import org.apache.commons.lang3.exception.ExceptionUtils._

trait ErrorMixin extends UpickleMixin with StringMixin {
  AnsiConsole.systemInstall();

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

  final def necToJson(errors: NonEmptyChain[Error]): ujson.Value = {
    write(
      errors.toChain.toList
    )
  }

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

  final def printException(
      timestamp: String,
      method: String,
      uri: String,
      cause: String
  ): Unit = {
    printError(s"Exception at $timestamp:".toUpperCase)

    printError("* Request:")
    printError(s"$method $uri", isColoured = false)

    printError("* Cause:")
    printError(cause, isColoured = false)
  }
}
