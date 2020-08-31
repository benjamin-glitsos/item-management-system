import cats.effect._
import org.http4s.implicits._
import scala.concurrent._
import org.http4s.server.Router

object Routes {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    val router = Router(
        s"/${sys.env.getOrElse("USERS_TABLE", "users")}" -> UsersRoutes.router
    ).orNotFound
}
