import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import java.util.UUID
import java.time.LocalDateTime
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
            PersonEdit(
                first_name = "fn2",
                last_name = "ln2",
                other_names = Some("on2"),
                sex_id = 1,
                email_address = "test@example.com",
                phone_number = "0444444444",
                address_line_one = "1 Test St",
                address_line_two = "Sydney",
                zip = "2000"
            ),
            UserEdit(
                username = "un3",
                password = "pw3"
            )
        ).transact(xa).unsafeRunSync
    }
}
