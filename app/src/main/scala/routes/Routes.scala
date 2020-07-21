import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import scala.concurrent._
import org.http4s.server.Router

object Routes {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    val service = Router(
        "/users" -> UsersRoutes.routes,
        "/other" -> HttpRoutes.of[IO] {
            case GET -> Root => Ok("yes")
        }
    ).orNotFound
}
