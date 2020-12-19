package docs.http.scaladsl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    implicit val system           = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val route =
      path("hello") {
        get {
          complete(
              HttpEntity(
                  ContentTypes.`text/html(UTF-8)`,
                  "<h1>Say hello to akka-http</h1>"
              )
          )
        }
      }

    val bindingFuture =
      Http()
        .newServerAt(
            System.getenv("DOCKER_LOCALHOST"),
            System.getenv("CONTROLLER_PORT").toInt
        )
        .bind(route)

    println("Server online at http://localhost/")

    var command_line = ""
    do {
      if (command_line != "" && command_line != "exit") {
        println(
            s"${Console.RED} The command '$command_line' does not exist. ${Console.WHITE}"
        )
      }
      command_line = StdIn
        .readLine(
            s"${Console.GREEN} Use 'exit' to shutdown the server... ${Console.WHITE}"
        )
        .stripMargin
        .stripLineEnd
    } while (command_line != "exit")
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
