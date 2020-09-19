import cats.effect._
import org.http4s.server.blaze._

object Main extends IOApp with LoggingUtilities with EnvUtilities {
    def run(args: List[String]): IO[ExitCode] = {

        Seeders.run()

        if(getEnvBool("ENABLE_SERVER")) {
            consoleHeading("Starting server")
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
        } else {
            IO.pure(ExitCode.Success)
        }
    }
}
