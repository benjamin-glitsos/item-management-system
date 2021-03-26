import cats.effect._
import scala.concurrent._
import doobie.{Meta => _, _}
import doobie.util.ExecutionContexts
import io.getquill.{idiom => _, _}
import doobie.quill.DoobieContext

package doobie_bundle {
  object connection {
    implicit final val contextShift: ContextShift[IO] =
      IO.contextShift(ExecutionContext.global)

    final val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver",
      s"jdbc:postgresql://database-postgresql/${System.getenv("POSTGRES_DATABASE")}",
      System.getenv("POSTGRES_USER"),
      System.getenv("POSTGRES_PASSWORD"),
      Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )
  }

  object database {
    final val dc = new DoobieContext.Postgres(SnakeCase)

    implicit final val cs: ContextShift[IO] =
      IO.contextShift(ExecutionContexts.synchronous)

    import dc._

    implicit final val usersWithMetaSchema =
      schemaMeta[UsersWithMeta]("users_with_meta")

    implicit final val usersListSchema =
      schemaMeta[UsersList]("users_list")
  }
}
