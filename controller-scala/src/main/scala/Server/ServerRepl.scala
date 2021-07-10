import scala.io.StdIn
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._

object ServerRepl {
  AnsiConsole.systemInstall();

  final def apply(): Unit = {
    println(
      ansi()
        .a("\n")
        .fg(MAGENTA)
        .a("The API is online at ")
        .bold()
        .a(s"http://localhost:${System.getenv("CONTROLLER_PORT")}/")
        .a("\n")
        .fg(MAGENTA)
        .a("The Admin Panel is online at ")
        .bold()
        .a(s"http://localhost:${System.getenv("ADMIN_PORT")}/")
        .reset()
        .a("\n")
    )

    var terminal_input: String = new String
    do {
      if (terminal_input != new String && terminal_input != "exit") {
        println(
          ansi()
            .fg(RED)
            .a(s"The command '$terminal_input' does not exist")
            .reset()
        );
      }

      terminal_input = StdIn
        .readLine(
          ansi()
            .fg(MAGENTA)
            .a("Use 'exit' to shutdown the server... \n")
            .reset()
            .toString
        )
        .stripMargin
        .stripLineEnd
    } while (terminal_input != "exit")
  }
}
