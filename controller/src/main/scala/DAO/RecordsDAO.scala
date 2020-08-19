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

object RecordsDAO {
    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    def insert(r: RecordEdit) = {
        val q = quote {
            query[Records].insert(
                _.uuid -> lift(r.uuid),
                _.created_at -> lift(LocalDateTime.now()),
                _.created_by -> lift(r.user_id)
            ).returningGenerated(_.id)
        }
        run(q)
    }

    def upsert(record: RecordEdit) = {
        sql"""
        INSERT INTO records (uuid, created_at, created_by)
        VALUES (${record.uuid}, NOW(), ${record.user_id})
        ON CONFLICT (uuid)
        DO UPDATE SET
              edits = records.edits + 1
            , edited_at = EXCLUDED.created_at
            , edited_by = EXCLUDED.created_by
        RETURNING id, edited_by
        """.query[RecordReturn].unique
    }
}
