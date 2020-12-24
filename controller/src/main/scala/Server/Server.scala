import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import org.fusesource.jansi.AnsiConsole
import scala.concurrent.Future
import akka.http.scaladsl.Http.ServerBinding

object Server {
  def apply(): Unit = {
    implicit val system           = ActorSystem(Behaviors.empty, "actor-system")
    implicit val executionContext = system.executionContext

    AnsiConsole.systemInstall();

    val host: String  = System.getenv("DOCKER_LOCALHOST")
    val port: Integer = System.getenv("CONTROLLER_PORT").toInt

    val bindingFuture: Future[ServerBinding] =
      Http()
        .newServerAt(host, port)
        .bind(Routes())

    ServerExitRepl()

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())

    println("Server shutdown complete.")
  }
}
