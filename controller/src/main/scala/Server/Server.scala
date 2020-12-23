import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import scala.io.StdIn
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._
import scala.concurrent.Future
import akka.http.scaladsl.Http.ServerBinding

object Server {
  def apply(): Unit = {
    implicit val system           = ActorSystem(Behaviors.empty, "actor-system")
    implicit val executionContext = system.executionContext

    AnsiConsole.systemInstall();

    val host: String  = System.getenv("DOCKER_LOCALHOST")
    val port: Integer = System.getenv("CONTROLLER_PORT").toInt

    // Server start --------------------

    val bindingFuture: Future[ServerBinding] =
      Http()
        .newServerAt(host, port)
        .bind(Routes())

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

    // Graceful shutdown --------------------

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
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
    println("Server shutdown complete.")
  }
}
