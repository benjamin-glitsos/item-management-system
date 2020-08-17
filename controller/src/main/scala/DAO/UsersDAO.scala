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

object UsersDAO {
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

    def list() = {
        val q = quote {
            query[Users]
        }
        run(q)
    }

    def insert(user: User) = {
        sql"""
        INSERT INTO users (record_id, staff_id, username, password)
        VALUES (
            ${user.record_id}
          , ${user.staff_id}
          , ${user.username}
          , ${user.password}
        )
        """.update.run
    }

    def update(user: User) = {
        sql"""
        UPDATE users SET
            username = ${user.username}
          , password = ${user.password}
        WHERE record_id = ${user.record_id}
        """.update.run
    }
}
