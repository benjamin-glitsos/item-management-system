import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._
import scala.concurrent._

object UsersRoute {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    val name = UsersSchema.baseTableRow.tableName

    def service = HttpRoutes.of[IO] {

        case GET -> Root / name =>
            Ok(IO.fromFuture(IO(UsersDAO.list)))

        case GET -> Root / name / IntVar(id) =>
            Ok(IO.fromFuture(IO(UsersDAO.show(id))))
            // TODO: nest this route within the one above for DRYness

    }.orNotFound
}

// TODO:
// users?page=1&sort.username=desc&search.username=lorem
// users/1?tab=person
