import cats.effect._
import scala.concurrent._
import doobie.{Meta => _, _}
import doobie.util.ExecutionContexts

trait DoobieConnectionMixin {
  implicit final val contextShift: ContextShift[IO] =
    IO.contextShift(ExecutionContext.global)

  final val transactor = Transactor.fromDriverManager[IO](
    "org.postgresql.Driver",
    s"jdbc:postgresql://database-postgresql/${System.getenv("POSTGRES_DATABASE")}",
    System.getenv("POSTGRES_USER"),
    System.getenv("POSTGRES_PASSWORD"),
    Blocker.liftExecutionContext(ExecutionContexts.synchronous)
  )
}
