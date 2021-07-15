import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import org.fusesource.jansi.AnsiConsole
import scala.concurrent.{Future, ExecutionContext}
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.settings.{ParserSettings, ServerSettings}

import redis.clients.jedis.Jedis

object Server extends SessionMixin {
  final def apply(): Unit = {
    val redis = new Jedis("session-redis")
    redis.auth(System.getenv("REDIS_PASSWORD"))
    redis.set("foo", "bar")
    val r = redis.get("foo")
    println(r)

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

    ServerRepl()

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())

    println("Server shutdown complete.")
  }
}
