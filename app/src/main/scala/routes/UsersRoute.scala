import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._

object UsersRoute {
    val service = HttpRoutes.of[IO] {
        case GET -> Root => Ok(UsersDAO.all)
    }.orNotFound
}

// TODO:
// users
// users?page=1&sort.username=desc&search.username=lorem
// users/1
// users/1?tab=person
