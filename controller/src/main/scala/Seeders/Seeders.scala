import cats.effect._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts

object Seeders extends LoggingUtilities with EnvUtilities {
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

    private def logSeederStatus(name: String) = {
        logSmallHeading(s"Populating ${name}")
    }

    private val staffCount = sys.env.getOrElse("STAFF_SEED_COUNT", "15").toInt

    def script() = {
        if(getEnvBool("ENABLE_SEEDER")) {
            logSeederStatus(StaffDAO.name)
            staffCount times StaffSeeder.create().transact(xa).unsafeRunSync

            logSeederStatus(UsersDAO.name)
            staffCount times UsersSeeder.create().transact(xa).unsafeRunSync
            UsersSeeder.populateAllStaffIds().transact(xa).unsafeRunSync
            // TODO: put all transacts inside one monad
        }
    }
}
