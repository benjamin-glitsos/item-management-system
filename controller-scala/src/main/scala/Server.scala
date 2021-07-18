import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import org.fusesource.jansi.AnsiConsole
import scala.concurrent.Future
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.settings.{ParserSettings, ServerSettings}
import scala.io.StdIn
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._

object Server extends SessionMixin with CustomHttpMethodsMixin with ErrorMixin {
  AnsiConsole.systemInstall();

  final val apiUri: String =
    s"http://localhost:${System.getenv("CONTROLLER_PORT")}"
  final val adminUri: String =
    s"http://localhost:${System.getenv("ADMIN_PORT")}"

  final def apply(): Unit = {
    implicit val system           = ActorSystem(Behaviors.empty, "actor-system")
    implicit val executionContext = system.executionContext

    val host: String  = "controller-scala"
    val port: Integer = System.getenv("CONTROLLER_PORT").toInt

    val parserSettings =
      ParserSettings
        .forServer(system)
        .withCustomMethods(allCustomHttpMethods: _*)
    val serverSettings =
      ServerSettings(system).withParserSettings(parserSettings)

    val bindingFuture: Future[ServerBinding] =
      Http()
        .newServerAt(host, port)
        .withSettings(serverSettings)
        .bind(Routes())

    System.out.println(
      ansi()
        .a("\n")
        .a(FilesDAO.open("banner.txt"))
        .fg(MAGENTA)
        .a(System.getenv("PROJECT_NAME").toUpperCase)
        .a("\n")
        .a("\n")
        .fg(MAGENTA)
        .a("The API is online at ")
        .bold()
        .a(s"$apiUri/")
        .a("\n")
        .fg(MAGENTA)
        .a("The Admin Panel is online at ")
        .bold()
        .a(s"$adminUri/")
        .reset()
        .a("\n")
    )

    var terminal_input: String = new String
    do {
      if (terminal_input != new String && terminal_input != "exit") {
        printError(s"The command '$terminal_input' does not exist")
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

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())

    System.out.println(
      ansi()
        .a("\n")
        .fg(MAGENTA)
        .a("Server shutdown complete.")
        .reset()
        .a("\n")
    )
  }
}
