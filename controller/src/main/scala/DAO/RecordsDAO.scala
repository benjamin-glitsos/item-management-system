import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._
import io.getquill.{ idiom => _, _ }
import doobie.quill.DoobieContext
import java.time.LocalDateTime
import java.util.UUID

object RecordsDAO {
    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    implicit val recordSchemaMeta = schemaMeta[Record]("records")

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    def insert(user_id: Int, notes: Option[String]) = {
        val q = quote {
            query[Record].insert(
                _.uuid -> lift(java.util.UUID.randomUUID()),
                _.created_at -> lift(LocalDateTime.now()),
                _.created_by -> lift(user_id),
                _.notes -> lift(notes)
            ).returningGenerated(_.id)
        }
        run(q)
    }

    def update(id: Int, user_id: Int, notes: Option[String]) = {
        val q = quote {
            query[Record]
                .filter(x => x.id == lift(id))
                .update(
                    u => u.edits -> (u.edits + 1),
                    _.edited_at -> Some(lift(LocalDateTime.now())),
                    _.edited_by -> Some(lift(user_id)),
                    _.notes -> lift(notes)
                )
        }
        run(q)
    }

    def delete(id: Int, user_id: Int) = {
        val q = quote {
            query[Record].filter(x => x.id == lift(id)).update(
                _.deleted_at -> Some(lift(LocalDateTime.now())),
                _.deleted_by -> Some(lift(user_id))
            )
        }
        run(q)
    }

    def restore(id: Int, user_id: Int) = {
        val q = quote {
            query[Record].filter(x => x.id == lift(id)).update(
                _.deleted_at -> None,
                _.deleted_by -> None,
                _.edited_at -> Some(lift(LocalDateTime.now())),
                _.edited_by -> Some(lift(user_id))
            )
        }
        run(q)
    }
}
