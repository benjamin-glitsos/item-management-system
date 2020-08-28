import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import org.http4s.server.blaze._



import scala.util.Random

object Main extends IOApp
with Seeder {
    def run(args: List[String]): IO[ExitCode] = {

        println(randomFixedDigits(1))
        println(randomFixedDigits(2))
        println(randomFixedDigits(3))
        println(randomFixedDigits(4))
        println(randomFixedDigits(5))
        println(randomFixedDigits(6))
        println(randomFixedDigits(7))
        println(randomFixedDigits(8))
        println(randomFixedDigits(9))
        println(randomFixedDigits(10))
        println(randomFixedDigits(11))
        println(randomFixedDigits(12))
        println(randomFixedDigits(13))
        println(randomFixedDigits(14))
        println(randomFixedDigits(15))

        // Seeders.script()

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
