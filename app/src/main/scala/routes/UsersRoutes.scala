import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._
import scala.concurrent._

object UsersRoutes {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    object Id extends QueryParamDecoderMatcher[Int]("id")
    object MaybeId extends OptionalQueryParamDecoderMatcher[Int]("id")
    object MaybeRows extends OptionalQueryParamDecoderMatcher[Int]("rows")
    object MaybePage extends OptionalQueryParamDecoderMatcher[Int]("page")

    val service = HttpRoutes.of[IO] {
        case GET -> Root :? MaybeId(maybeId) +& MaybeRows(maybeRows) +& MaybePage(maybePage) => {
            val rows = maybeRows.getOrElse(25)
            val page = maybePage.getOrElse(1)
            maybeId match {
              case None =>
                Ok(IO.fromFuture(IO(UsersDAO.list(rows, page))))
              case Some(id) =>
                IO.fromFuture(IO(UsersDAO.show(id))).flatMap(_.fold(NotFound())(Ok(_)))
                // TODO: split this into separate tabs by a url parameter. default = first tab
            }
        }
        case DELETE -> Root :? Id(id) =>
            Ok(IO.fromFuture(IO(UsersDAO.delete(id))))
    }
}

// TODO: join: records -> people -> users. Then later the first two can be abstracted out and applied to many queries
