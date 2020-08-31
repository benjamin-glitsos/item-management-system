import cats._
import cats.data._
import cats.effect._
import cats.implicits._

object Main extends IOApp with BlazeImports {
    def run(args: List[String]): IO[ExitCode] = {

        Seeders.script()

        blaze[IO]
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
