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
        // UsersServices.insert(
        //     User(
        //         id = 0,
        //         record_id = 0,
        //         staff_id = 1,
        //         username = "un90",
        //         password = "pw90"
        //     ),
        //     user_id = 1,
        //     notes = None
        // ).transact(xa).unsafeRunSync

        // UsersServices.update(
        //     User(
        //         id = 2,
        //         record_id = 8,
        //         staff_id = 1,
        //         username = "un9999",
        //         password = "pw9999"
        //     ),
        //     user_id = 1,
        //     notes = Some("Test of updating notes.")
        // ).transact(xa).unsafeRunSync

        // UsersServices.delete(
        //     record_id = 3,
        //     user_id = 1
        // ).transact(xa).unsafeRunSync

        // UsersServices.restore(
        //     record_id = 3,
        //     user_id = 1
        // ).transact(xa).unsafeRunSync

        println(UsersDAO.list().transact(xa).unsafeRunSync)
    }
}
