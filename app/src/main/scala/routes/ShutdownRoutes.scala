import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._
import scala.concurrent._

object ShutdownRoutes {
    implicit val ec: ExecutionContext = ExecutionContext.global

    val routes = HttpRoutes.of[IO] {
        case GET -> Root => {
            Ok("Bye")
        }
    }
}
