import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import org.http4s.server.blaze._



import scala.util.Random

object Main extends IOApp
with Seeder {
    def run(args: List[String]): IO[ExitCode] = {

        // Seeders.script()

        println("0/10")
        println(randomExists(0, "wow"))
        println(randomExists(0, "wow"))
        println(randomExists(0, "wow"))
        println(randomExists(0, "wow"))
        println(randomExists(0, "wow"))
        println("3/10")
        println(randomExists(0.3, "wow"))
        println(randomExists(0.3, "wow"))
        println(randomExists(0.3, "wow"))
        println(randomExists(0.3, "wow"))
        println(randomExists(0.3, "wow"))
        println("5/10")
        println(randomExists(0.5, "wow"))
        println(randomExists(0.5, "wow"))
        println(randomExists(0.5, "wow"))
        println(randomExists(0.5, "wow"))
        println(randomExists(0.5, "wow"))
        println("7/10")
        println(randomExists(0.7, "wow"))
        println(randomExists(0.7, "wow"))
        println(randomExists(0.7, "wow"))
        println(randomExists(0.7, "wow"))
        println(randomExists(0.7, "wow"))
        println("9/10")
        println(randomExists(0.9, "wow"))
        println(randomExists(0.9, "wow"))
        println(randomExists(0.9, "wow"))
        println(randomExists(0.9, "wow"))
        println(randomExists(0.9, "wow"))
        println("10/10")
        println(randomExists(1, "wow"))
        println(randomExists(1, "wow"))
        println(randomExists(1, "wow"))
        println(randomExists(1, "wow"))
        println(randomExists(1, "wow"))

        BlazeServerBuilder[IO]
            .bindHttp(
                System.getenv("CONTROLLER_PORT").toInt,
                System.getenv("DOCKER_LOCALHOST")
            )
            .withHttpApp(Routes.router)
            .serve
            .compile
            .drain
            .as(ExitCode.Success)
    }
}
