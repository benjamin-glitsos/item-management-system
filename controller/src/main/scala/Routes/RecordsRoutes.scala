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

object RecordsRoutes {
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
        case DELETE -> Root / IntVar(record_id) :? MaybeRestore(maybeRestore) => {
            maybeRestore match {
                case None => {
                    Ok(RecordsServices.delete(
                        record_id,
                        user_id = 1
                    ).transact(xa).unsafeRunSync)
                }
                case Some(isRestore) => {
                    Ok(RecordsServices.restore(
                        record_id,
                        user_id = 1
                    ).transact(xa).unsafeRunSync)
                }
            }
        }
    }
}
