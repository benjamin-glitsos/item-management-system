import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._
import scala.concurrent.ExecutionContext.global

object Server extends IOApp with EnvUtilities with LoggingUtilities {
    def run(args: List[String]): IO[ExitCode] = {
        if(getEnvBool("ENABLE_SERVER")) {
            logSmallHeading("Starting Server")
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
