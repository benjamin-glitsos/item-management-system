import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import java.util.UUID
import java.time.LocalDateTime

object Main {
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    def upsertAll(record: RecordEdit, user: User): Update0 =
        sql"""
        | WITH up_record AS (
        |     INSERT INTO records (uuid, created_at, created_by)
        |     VALUES (${record.uuid}, ${record.time}, ${record.user_id})
        |     ON CONFLICT (uuid)
        |     DO UPDATE SET updated_at = EXCLUDED.created_at, updated_by = EXCLUDED.created_by
        |     RETURNING id
        | )
        | INSERT INTO users (person_id, username, password)
        | VALUES (${user.person_id}, ${user.username}, ${user.password})
        | ON CONFLICT (username)
        | DO UPDATE SET person_id = EXCLUDED.person_id, password = EXCLUDED.password
        """.update

    def main(args: Array[String]) {
        upsertAll(
            record = RecordEdit(
                uuid = UUID.randomUUID,
                time = LocalDateTime.now(),
                user_id = 1
            ),
            user = User(
                id = 0,
                person_id = 0,
                username = "un4",
                password = "pw6"
            )
        ).run.transact(xa).unsafeRunSync
    }
}
