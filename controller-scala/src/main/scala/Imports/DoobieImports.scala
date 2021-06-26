import cats.effect._
import scala.concurrent._
import doobie.{Meta => DoobieMeta, _}
import doobie.util.ExecutionContexts
import io.getquill.{idiom => _, _}
import doobie.quill.DoobieContext
import org.joda.time.LocalDateTime

package doobie_import {
  object connection {
    implicit final val contextShift: ContextShift[IO] =
      IO.contextShift(ExecutionContext.global)

    final val transactor = Transactor.fromDriverManager[IO](
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

    implicit final val usersOpenSchema =
      schemaMeta[UsersOpen]("users_open")
    implicit final val usersListSchema =
      schemaMeta[UsersList]("users_list")

    implicit final val itemsOpenSchema =
      schemaMeta[ItemsOpen]("items_open")
    implicit final val itemsListSchema =
      schemaMeta[ItemsList]("items_list")

    implicit val jodaLocalDateTimeMeta: DoobieMeta[LocalDateTime] =
      DoobieMeta[LocalDateTime].timap(LocalDateTimeUtilities.format(x))(x =>
        x => LocalDateTimeUtilities.parse(x)
      )
  }
}
