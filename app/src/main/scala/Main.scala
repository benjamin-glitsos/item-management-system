import cats.effect._
import org.http4s.server.blaze._



import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._
// import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe._
import io.circe.syntax._
import io.circe.generic.auto._

import scala.concurrent._
// import scala.concurrent.ExecutionContext.global
import scala.concurrent.ExecutionContext.Implicits.global


object Main extends IOApp  {
    def run(args: List[String]): IO[ExitCode] = {
        val service = HttpRoutes.of[IO] {
            case GET -> Root =>
                Ok(IO.fromFuture(IO(UsersDAO.list)).map((x: Seq[User]) => (x.asJson)))
        }.orNotFound

        // Data.setup()
        //
        BlazeServerBuilder[IO]
            .bindHttp(
                System.getenv("APP_PORT").toInt,
                System.getenv("DOCKER_LOCALHOST")
            )
            .withHttpApp(service)
            .serve
            .compile
            .drain
            .as(ExitCode.Success)
    }
}
