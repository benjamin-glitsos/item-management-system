import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._
import doobie.implicits._
import bundles.doobie.connection._
import bundles.http4s._

import cats.data.Validated.{Invalid, Valid}

object UsersRoutes {
    val router = HttpRoutes.of[IO] {
        case GET -> Root :? MaybeNumber(maybeNumber) +& MaybeLength(maybeLength) => {
            Ok(UsersServices.list(maybeNumber, maybeLength).transact(xa).unsafeRunSync)
        }

        case GET -> Root / username => {
            UsersServices.open(
                username,
                user_id = 1
            ).transact(xa).unsafeRunSync match {
                case Valid(u) => Ok(u)
                case Invalid(e) => NotFound(e)
            }

            // Ok(UsersServices.open(
            //     username,
            //     user_id = 1
            // ).transact(xa).unsafeRunSync)
        }

        // TODO: accept json body like this:
        // val jsonApp = HttpRoutes.of[IO] {
        //   case req @ POST -> Root / "hello" =>
        //     for {
        //       // Decode a User request
        //       user <- req.as[User]
        //       // Encode a hello response
        //       resp <- Ok(Hello(user.name).asJson)
        //     } yield (resp)
        // }.orNotFound
        case POST -> Root => {
            Ok(UsersServices.create(
                User(
                    id = 0,
                    record_id = 0,
                    staff_id = 1,
                    username = "un90",
                    password = "pw90"
                ),
                user_id = 1,
                notes = None
            ).transact(xa).unsafeRunSync)
        }

        case PUT -> Root => {
            Ok(UsersServices.edit(
                User(
                    id = 2,
                    record_id = 8,
                    staff_id = 1,
                    username = "un9999",
                    password = "pw9999"
                ),
                user_id = 1,
                notes = Some("Test of updating notes.")
            ).transact(xa).unsafeRunSync)
        }

        // case DELETE -> Root / username / action => {
        //     action match {
        //         case "soft" => {
        //             Ok(UsersServices.delete(
        //                 username,
        //                 user_id = 1
        //             ).transact(xa).unsafeRunSync)
        //         }
        //         case "restore" => {
        //             Ok(UsersServices.restore(
        //                 username,
        //                 user_id = 1
        //             ).transact(xa).unsafeRunSync)
        //         }
        //         case "hard" => {
        //             Ok(UsersServices.permanentlyDelete(username)
        //                 .transact(xa).unsafeRunSync)
        //         }
        //     }
        // }
    }
}
