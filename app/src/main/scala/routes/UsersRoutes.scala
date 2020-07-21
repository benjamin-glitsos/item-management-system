import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._
import scala.concurrent._
import org.http4s.server.Router

object UsersRoutes {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    val routes = HttpRoutes.of[IO] {
        case GET -> Root => Ok(IO.fromFuture(IO(UsersDAO.list)))

        // case GET -> Root / IntVar(id) =>
        //     Ok(IO.fromFuture(IO(UsersDAO.show(id))))
        //
        // case DELETE -> Root / IntVar(id) =>
        //     Ok(IO.fromFuture(IO(UsersDAO.delete(id))))
    }
}

// TODO: join: records -> people -> users. Then later the first two can be abstracted out and applied to many queries

// TODO:
// users?page=1&sort.username=desc&search.username=lorem
// users/1?tab=person
