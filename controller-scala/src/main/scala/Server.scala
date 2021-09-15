import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import org.fusesource.jansi.AnsiConsole
import akka.http.scaladsl.settings.{ParserSettings, ServerSettings}
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._

object Server extends SessionMixin with HttpMethodsMixin {
  implicit val system           = ActorSystem(Behaviors.empty, "actor-system")
  implicit val executionContext = system.executionContext

  AnsiConsole.systemInstall();

  final val apiUri: String =
    s"http://localhost:${System.getenv("CONTROLLER_PORT")}"
  final val adminUri: String =
    s"http://localhost:${System.getenv("ADMIN_PORT")}"
  final val host: String  = "controller-scala"
  final val port: Integer = System.getenv("CONTROLLER_PORT").toInt

  private val parserSettings: ParserSettings =
    ParserSettings
      .forServer(system)
      .withCustomMethods(allCustomHttpMethods: _*)

  private val serverSettings: ServerSettings =
    ServerSettings(system).withParserSettings(parserSettings)

  final def apply(): Unit = {
    Http()
      .newServerAt(host, port)
      .withSettings(serverSettings)
      .bind(Routes())

    System.out.println(
      ansi()
        .a(FilesDAO.openPrivateFile("resources/banner.txt"))
        .fg(BLUE)
        .a("The API is online at ")
        .bold()
        .a(s"$apiUri/")
        .a("\n")
        .fg(BLUE)
        .a("The Admin Panel is online at ")
        .bold()
        .a(s"$adminUri/")
        .reset()
    )
  }
}
