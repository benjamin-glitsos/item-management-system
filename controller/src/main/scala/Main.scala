import cats.effect._
import org.http4s.server.blaze._

object Main extends IOApp with LoggingUtilities {
    def run(args: List[String]): IO[ExitCode] = {

        // Seeders.script()

        // TODO: put this into Router/Server.scala
        // TODO: make an env yes/no config for whether to start server or just return ExitCode
        // TODO: write at least one test case for MVP
        logSmallHeading("Starting server")
        println(UserValidators.isPasswordValid(password = "wow"))
        println(UserValidators.isPasswordValid(password = "Benji123!"))
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
