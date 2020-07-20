import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe._
import io.circe.syntax._
import io.circe.generic.auto._

import scala.concurrent._
import scala.concurrent.ExecutionContext.global
// import scala.concurrent.ExecutionContext.Implicits.global

object UsersRoute {
    implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

    def service = HttpRoutes.of[IO] {
        case GET -> Root =>
            Ok(IO.fromFuture(IO(UsersDAO.list)))
    }.orNotFound
}

// TODO:
// users
// users?page=1&sort.username=desc&search.username=lorem
// users/1
// users/1?tab=person
