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
        println(randomFixedDigits(9).toString)
        println(randomFixedDigits(10).toString)
        println(randomFixedDigits(11).toString)
        println(randomFixedDigits(12).toString)
        println(randomFixedDigits(13).toString)
        println(randomFixedDigits(14).toString)
        println(randomFixedDigits(15).toString)

        println(biasedFlip(1/3))
        println(biasedFlip(1/3))
        println(biasedFlip(1/3))
        println(biasedFlip(1/3))
        println(biasedFlip(1/3))
        println(biasedFlip(1/3))
        println(biasedFlip(1/3))
        println(biasedFlip(2/3))
        println(biasedFlip(2/3))
        println(biasedFlip(2/3))
        println(biasedFlip(2/3))
        println(biasedFlip(2/3))
        println(biasedFlip(2/3))
        println(biasedFlip(2/3))
        println(biasedFlip(2/3))

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
