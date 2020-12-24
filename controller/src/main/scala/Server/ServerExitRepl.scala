import scala.io.StdIn
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._

object ServerExitRepl {
  AnsiConsole.systemInstall();

  def apply() = {
    println(
      ansi()
        .a("\n")
        .fg(BLUE)
        .a("The server is online at ")
        .bold()
        .a(s"http://localhost/")
        .reset()
        .a("\n")
    )

    var terminal_input: String = ""
    do {
      if (terminal_input != "" && terminal_input != "exit") {
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
            .fg(GREEN)
            .a("Use 'exit' to shutdown the server... \n")
            .reset()
            .toString()
        )
        .stripMargin
        .stripLineEnd
    } while (terminal_input != "exit")
  }
}
