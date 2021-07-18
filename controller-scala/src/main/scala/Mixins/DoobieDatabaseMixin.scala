import cats.effect._
import doobie.{Meta => _, _}
import doobie.util.ExecutionContexts
import io.getquill.{idiom => _, _}
import doobie.quill.DoobieContext

trait DoobieDatabaseMixin {
  final val dc = new DoobieContext.Postgres(SnakeCase)

  implicit final val cs: ContextShift[IO] =
    IO.contextShift(ExecutionContexts.synchronous)

  import dc._

  implicit final val usersOpenSchema =
    schemaMeta[UsersOpen]("users_open")
  implicit final val usersListSchema =
    schemaMeta[UsersList]("users_list")

  implicit final val itemsOpenSchema =
    schemaMeta[ItemsOpen]("items_open")
  implicit final val itemsListSchema =
    schemaMeta[ItemsList]("items_list")
}
