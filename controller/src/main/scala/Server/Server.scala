import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn
import org.fusesource.jansi.AnsiConsole
import org.fusesource.jansi.Ansi._
import org.fusesource.jansi.Ansi.Color._

object Server {
  def apply(): Unit = {
    implicit val system           = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    AnsiConsole.systemInstall();

    // Server start --------------------

    val bindingFuture =
      Http()
        .newServerAt(
            System.getenv("DOCKER_LOCALHOST"),
            System.getenv("CONTROLLER_PORT").toInt
        )
        .bind(Routes())

    println("Server is online at http://localhost/")

    // Graceful shutdown REPL --------------------

    var command_line = ""
    do {
      if (command_line != "" && command_line != "exit") {
        println(
            ansi()
              .fg(RED)
              .a(s"The command '$command_line' does not exist")
              .reset()
        );
      }
      command_line = StdIn
        .readLine(
            ansi()
              .fg(GREEN)
              .a("Use 'exit' to shutdown the server... ")
              .reset()
              .toString()
        )
        .stripMargin
        .stripLineEnd
    } while (command_line != "exit")
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
