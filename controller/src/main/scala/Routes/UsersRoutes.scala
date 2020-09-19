import java.time.LocalDateTime
import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.Json
import org.http4s.circe._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._
import doobie.implicits._
import bundles.doobie.connection._
import bundles.http4s._
import cats.data.Validated.{Invalid, Valid}

import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

object UsersRoutes extends ValidationUtilities {
    // implicit val keyValueDecoder: KeyDecoder[String] = new KeyDecoder[String] {
    //   override def apply(key: String): Option[String] = Some(key)
    // }

    val router = HttpRoutes.of[IO] {
        case GET -> Root :? MaybeNumber(maybeNumber) +& MaybeLength(maybeLength) => {
            Ok(UsersServices.list(maybeNumber, maybeLength).transact(xa).unsafeRunSync)
        }

        case GET -> Root / username => {
            UsersServices.open(
                username,
                user_id = 1
            ).transact(xa).unsafeRunSync match {
                case Valid(v) => Ok(v)
                case Invalid(e) => NotFound(e)
            }
        }

        case req @ POST -> Root => {
            for {
              fields <- Ok(req.as[Json])
              // val user = User(
              //     id = 2,
              //     record_id = 8,
              //     staff_id = 1,
              //     username = "un9999",
              //     password = "pw9999"
              // ).validNel
              // val user_id = Validators.getField(required = true, "user_id", fields)
              // val notes = Validators.getField(required = false, "notes", fields)
              // println(user *> user_id *> notes)
              // TODO: out of this map try to get the user object, user id and notes. if you cant get required things then you can return invalid
              // and at this stage (before converting to an object), you can validate the fields
              // (Use the key name (a string) to pass to the Error as the field name)
              // then if all this is still Valid then you can run the Service which will return Valid if all the DAOs within return Valid
              // then if that is Valid this returns Ok. Otherwise returns BadRequest or NotFound
              // res <- Ok(
              //     UsersServices
              //         .create(user, user_id, notes)
              //         .transact(xa).unsafeRunSync
              //     )
            } yield (fields)
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
