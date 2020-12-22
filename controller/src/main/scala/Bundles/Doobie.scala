import cats.effect._
import scala.concurrent._
import doobie.{Meta => _, _}
import doobie.util.ExecutionContexts
import io.getquill.{idiom => _, _}
import doobie.quill.DoobieContext
import io.getquill.{idiom => _, _}

package bundles {
  object doobie {
    object connection {
      implicit val contextShift: ContextShift[IO] =
        IO.contextShift(ExecutionContext.global)

      val xa = Transactor.fromDriverManager[IO](
          "org.postgresql.Driver",
          s"jdbc:postgresql://database/${System.getenv("POSTGRES_DATABASE")}",
          System.getenv("POSTGRES_USER"),
          System.getenv("POSTGRES_PASSWORD"),
          Blocker.liftExecutionContext(ExecutionContexts.synchronous)
      )
    }

    object database {
      val dc = new DoobieContext.Postgres(SnakeCase)

      implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

      import dc._

      implicit val usersWithMetaSchema =
        schemaMeta[UsersWithMeta]("users_with_meta")
    }
  }
}
