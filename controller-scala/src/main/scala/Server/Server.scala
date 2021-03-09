import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import org.fusesource.jansi.AnsiConsole
import scala.concurrent.{Future, ExecutionContext}
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.settings.{ParserSettings, ServerSettings}

object Server {
  final def apply(): Unit = {
    implicit val system                             = ActorSystem(Behaviors.empty, "actor-system")
    implicit val executionContext: ExecutionContext = system.executionContext

    AnsiConsole.systemInstall();

    val host: String  = "controller_scala"
    val port: Integer = System.getenv("CONTROLLER_PORT").toInt

    val parserSettings =
      ParserSettings.forServer(system).withCustomMethods(CustomMethods(): _*)
    val serverSettings =
      ServerSettings(system).withParserSettings(parserSettings)

    val bindingFuture: Future[ServerBinding] =
      Http()
        .newServerAt(host, port)
        .withSettings(serverSettings)
        .bind(Routes())

    ServerExitRepl()

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())

    println("Server shutdown complete.")
  }
}
