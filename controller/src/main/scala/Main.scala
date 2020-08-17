import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import java.util.UUID
import java.time.LocalDateTime
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._

object Main {
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    def main(args: Array[String]) {
        UsersServices.upsert(
            RecordEdit(
                uuid = java.util.UUID.randomUUID,
                user_id = 1
            ),
            User(
                id = 0,
                record_id = 0,
                staff_id = 1,
                username = "un3",
                password = "pw3"
            )
        ).transact(xa).unsafeRunSync
    }
}
