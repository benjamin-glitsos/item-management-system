import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import org.http4s.server.blaze._



import scala.util.Random

object Main extends IOApp
with Seeder {
    def run(args: List[String]): IO[ExitCode] = {

        Seeders.script()

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
