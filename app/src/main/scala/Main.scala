import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._

object Main extends IOApp  {
    // def main(args: Array[String]): Unit = {
    //     val setup = Data.setup
    // }

    val service = HttpRoutes.of[IO] {
        case GET -> Root => Ok("root")
    }.orNotFound

    def run(args: List[String]): IO[ExitCode] =
        BlazeServerBuilder[IO]
            .bindHttp(System.getenv("APP_PORT").toInt, System.getenv("DOCKER_LOCALHOST"))
            .withHttpApp(service)
            .serve
            .compile
            .drain
            .as(ExitCode.Success)
}
