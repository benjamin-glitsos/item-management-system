import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._
import org.http4s.server.blaze._

object Seeders {
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    def load(count: Int, nameEnv: String, nameAlt: String, fn: => Unit) = {
        (1 to count) foreach (x => fn)
        println(s"Populated ${sys.env.getOrElse(nameEnv, nameAlt)}")
    }

    def script() = {
        load(
            count = 15,
            nameEnv = "USERS_TABLE",
            nameAlt = "users",
            fn = UsersLoader.create().transact(xa).unsafeRunSync
        )
    }
}
