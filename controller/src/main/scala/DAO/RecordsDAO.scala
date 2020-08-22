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
    implicit val usersSchemaMeta = schemaMeta[User]("users")

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    def insert(user_id: Int, notes: Option[String]) = {
        run(quote(
            query[Record].insert(
                _.uuid -> lift(java.util.UUID.randomUUID()),
                _.created_at -> lift(LocalDateTime.now()),
                _.created_by -> lift(user_id),
                _.notes -> lift(notes)
            ).returningGenerated(_.id)
        ))
    }

    def view(id: Int, user_id: Int) = {
        run(quote(
            query[Record]
                .filter(x => x.id == lift(id))
                .update(
                    x => x.views -> (x.views + 1),
                    _.viewed_at -> Some(lift(LocalDateTime.now())),
                    _.viewed_by -> Some(lift(user_id))
                )
        ))
    }

    def update(id: Int, user_id: Int, notes: Option[String]) = {
        run(quote(
            query[Record]
                .filter(x => x.id == lift(id))
                .update(
                    x => x.edits -> (x.edits + 1),
                    _.edited_at -> Some(lift(LocalDateTime.now())),
                    _.edited_by -> Some(lift(user_id)),
                    _.notes -> lift(notes)
                )
        ))
    }

    def delete(id: Int, user_id: Int) = {
        run(quote(
            query[Record].filter(x => x.id == lift(id)).update(
                x => x.deletions -> (x.deletions + 1),
                _.deleted_at -> Some(lift(LocalDateTime.now())),
                _.deleted_by -> Some(lift(user_id))
            )
        ))
    }

    def restore(id: Int, user_id: Int) = {
        run(quote(
            query[Record].filter(x => x.id == lift(id)).update(
                _.deleted_at -> None,
                _.deleted_by -> None,
                _.restored_at -> Some(lift(LocalDateTime.now())),
                _.restored_by -> Some(lift(user_id))
            )
        ))
    }

    def open(id: Int) = {
        run(quote(
            (for {
                r <- query[Record].filter(_.id == lift(id))
                creator <- query[User].join(_.id == r.created_by)
                viewer <- query[User].leftJoin(x => r.viewed_by.exists(_ == x.id))
                editor <- query[User].leftJoin(x => r.edited_by.exists(_ == x.id))
                deletor <- query[User].leftJoin(x => r.deleted_by.exists(_ == x.id))
                restorer <- query[User].leftJoin(x => r.restored_by.exists(_ == x.id))
            } yield (
                RecordOpen(
                    uuid = r.uuid,
                    created_at = r.created_at,
                    created_by = creator.username,
                    views = r.views,
                    viewed_at = r.viewed_at,
                    viewed_by = viewer.map(_.username),
                    edits = r.edits,
                    edited_at = r.edited_at,
                    edited_by = editor.map(_.username),
                    deletions = r.deletions,
                    deleted_at = r.deleted_at,
                    deleted_by = deletor.map(_.username),
                    restored_at = r.restored_at,
                    restored_by = restorer.map(_.username),
                    notes = r.notes
                )
            ))
        ))
    }
}
