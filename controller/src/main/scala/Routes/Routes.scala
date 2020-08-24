import cats.effect._
import org.http4s.implicits._
import scala.concurrent._
import org.http4s.server.Router

object Routes {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    val router = Router(
        s"/${System.getenv("USERS_TABLE")}" -> UsersRoutes.router,
        s"/${System.getenv("RECORDS_TABLE")}" -> RecordsRoutes.router
    ).orNotFound
}
