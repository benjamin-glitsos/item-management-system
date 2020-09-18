import cats.effect._
import org.http4s.server.blaze._

object Main extends IOApp with LoggingUtilities with EnvUtilities {
    def run(args: List[String]): IO[ExitCode] = {

        Seeders.script()

        // TODO: write at least one test case for MVP
        logSmallHeading("Starting server")
        // println(UserValidators.isPasswordValid(password = "wow"))
        // println(UserValidators.isPasswordValid(password = "Benji123!"))
        if(getEnvBool("ENABLE_SERVER")) {
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
