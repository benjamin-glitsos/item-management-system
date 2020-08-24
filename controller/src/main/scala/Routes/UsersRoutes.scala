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

    object MaybeRestore extends OptionalQueryParamDecoderMatcher[String]("restore")

    val router = HttpRoutes.of[IO] {
        case GET -> Root => {
            Ok(UsersServices.list(
                Page(
                    number = 1,
                    length = 25
                )
            ).transact(xa).unsafeRunSync)
        }

        case GET -> Root / username => {
            Ok(UsersServices.open(
                username,
                user_id = 1
            ).transact(xa).unsafeRunSync)
        }

        case POST -> Root => {
            Ok(UsersServices.create(
                User(
                    id = 0,
                    record_id = 0,
                    staff_id = 1,
                    username = "un90",
                    password = "pw90"
                ),
                user_id = 1,
                notes = None
            ).transact(xa).unsafeRunSync)
        }

        case PUT -> Root => {
            Ok(UsersServices.edit(
                User(
                    id = 2,
                    record_id = 8,
                    staff_id = 1,
                    username = "un9999",
                    password = "pw9999"
                ),
                user_id = 1,
                notes = Some("Test of updating notes.")
            ).transact(xa).unsafeRunSync)
        }

        case DELETE -> Root :? MaybeRestore(maybeRestore) => {
            maybeRestore match {
                case None => {
                    Ok(UsersServices.delete(
                        record_id = 3,
                        user_id = 1
                    ).transact(xa).unsafeRunSync)
                }
                case Some(isRestore) => {
                    Ok(UsersServices.restore(
                        record_id = 3,
                        user_id = 1
                    ).transact(xa).unsafeRunSync)
                }
            }
        }
    }
}


// object Id extends QueryParamDecoderMatcher[Int]("id")
// object MaybeId extends OptionalQueryParamDecoderMatcher[Int]("id")
// object MaybeRows extends OptionalQueryParamDecoderMatcher[Int]("rows")
// object MaybePage extends OptionalQueryParamDecoderMatcher[Int]("page")

// case Some(id) =>
//     IO.fromFuture(IO(UsersService.show(id))).flatMap(_.fold(NotFound())(Ok(_)))

// TODO: move these into the Service. The parameters will take maybeRows, maybePage

// :? MaybeId(maybeId) +& MaybeRows(maybeRows) +& MaybePage(maybePage)

// val rows = maybeRows.getOrElse(25)
// val page = maybePage.getOrElse(1)
