import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._
import scala.concurrent._
import cats._
import cats.data._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._

object UsersRoutes {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    val router = HttpRoutes.of[IO] {
        case GET -> Root => {
            Ok(UsersServices.list(
                Page(
                    number = 1,
                    length = 25
                )
            ).transact(xa).unsafeRunSync)
        }
    }
}

// object Id extends QueryParamDecoderMatcher[Int]("id")
// object MaybeId extends OptionalQueryParamDecoderMatcher[Int]("id")
// object MaybeRows extends OptionalQueryParamDecoderMatcher[Int]("rows")
// object MaybePage extends OptionalQueryParamDecoderMatcher[Int]("page")

// case DELETE -> Root :? Id(id) =>
//     Ok(IO.fromFuture(IO(UsersService.delete(id))))

// case Some(id) =>
//     IO.fromFuture(IO(UsersService.show(id))).flatMap(_.fold(NotFound())(Ok(_)))

// TODO: move these into the Service. The parameters will take maybeRows, maybePage

// :? MaybeId(maybeId) +& MaybeRows(maybeRows) +& MaybePage(maybePage)

// val rows = maybeRows.getOrElse(25)
// val page = maybePage.getOrElse(1)

// maybeId match {
//     case None =>
// }
