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

    implicit def intTimes(i: Int) = new {
        def times(fn: => Unit) = (1 to i) foreach (x => fn)
    }

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    def log(nameEnv: String, nameAlt: String) = {
        println(s"Populated ${sys.env.getOrElse(nameEnv, nameAlt)}")
    }

    def script() = {
        15 times UsersSeeder.create().transact(xa).unsafeRunSync
        UsersSeeder.populateAllStaffIds().transact(xa).unsafeRunSync
        log("USERS_TABLE", "users")
    }
}
