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

    val route = get {
      concat(
          path("hello") {
            complete("Wow")
          },
          path("world") {
            complete("Cool")
          }
      )
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
            Console.RED + s"The command '$command_line' does not exist." + Console.WHITE
        )
      }
      command_line = StdIn
        .readLine(
            Console.GREEN + "Use 'exit' to shutdown the server... " + Console.WHITE
        )
        .stripMargin
        .stripLineEnd
    } while (command_line != "exit")
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
