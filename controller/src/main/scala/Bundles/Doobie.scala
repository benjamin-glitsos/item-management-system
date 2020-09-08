import cats.effect._
import scala.concurrent._
import cats._
import cats.data._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import io.getquill.{ idiom => _, _ }
import doobie.quill.DoobieContext
import doobie.postgres.implicits._

package bundles {
    object doobie {
        object connection {
            implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

            val xa = Transactor.fromDriverManager[IO](
                "org.postgresql.Driver",
                s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
                System.getenv("POSTGRES_USER"),
                System.getenv("POSTGRES_PASSWORD"),
                Blocker.liftExecutionContext(ExecutionContexts.synchronous)
            )
        }

        object database {
            val dc = new DoobieContext.Postgres(SnakeCase)
        }
    }
}
