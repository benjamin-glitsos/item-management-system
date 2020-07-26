import cats.effect._
import org.http4s.server.blaze._

object Main extends IOApp {

    def run(args: List[String]): IO[ExitCode] = {
        Loader.setup()

        BlazeServerBuilder[IO]
            .bindHttp(
                System.getenv("APP_PORT").toInt,
                System.getenv("DOCKER_LOCALHOST")
            )
            .withHttpApp(Routes.service)
            .serve
            .compile
            .drain
            .as(ExitCode.Success)
    }
}
