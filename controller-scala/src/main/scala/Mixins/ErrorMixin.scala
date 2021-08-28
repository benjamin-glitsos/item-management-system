import cats.data.NonEmptyChain
import upickle.default._
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._
import org.apache.commons.lang3.exception.ExceptionUtils._

trait ErrorMixin extends UpickleMixin with StringMixin {
  AnsiConsole.systemInstall();

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
}
