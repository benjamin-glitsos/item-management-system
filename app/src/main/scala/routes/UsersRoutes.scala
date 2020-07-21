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

    object Id extends QueryParamDecoderMatcher[Int]("id")
    object MaybeId extends OptionalQueryParamDecoderMatcher[Int]("id")

    val routes = HttpRoutes.of[IO] {
        case GET -> Root :? MaybeId(maybeId) =>
            maybeId match {
              case None =>
                Ok(IO.fromFuture(IO(UsersDAO.list)))
              case Some(id) =>
                Ok(IO.fromFuture(IO(UsersDAO.show(id))))
            }
        case DELETE -> Root :? Id(id) =>
            Ok(IO.fromFuture(IO(UsersDAO.delete(id))))
    }
}

// TODO: join: records -> people -> users. Then later the first two can be abstracted out and applied to many queries

// TODO:
// users?page=1&sort.username=desc&search.username=lorem
// users/1?tab=person
