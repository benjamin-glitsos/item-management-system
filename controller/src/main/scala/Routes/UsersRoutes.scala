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
import java.sql.SQLException

object UsersRoutes extends ValidationUtilities {
    val router = HttpRoutes.of[IO] {
        case GET -> Root :? MaybeNumber(maybeNumber) +& MaybeLength(maybeLength) => { // TODO: move these query params into the body?
            PartialContent(UsersServices.list(maybeNumber, maybeLength).transact(xa).unsafeRunSync)
        }

        // case GET -> Root / username => {
        //     UsersServices.open(username).transact(xa).unsafeRunSync match {
        //         case Invalid(e) => NotFound(e)
        //         case Valid(v) => Ok(v)
        //     }
        // }

        case body @ POST -> Root => {
            for {
                json <- body.as[Json]

                val username = Validators.getRequiredField("username", json)
                val password = Validators.getRequiredField("password", json)
                val user_username = Validators.getRequiredField("user_username", json)
                val notes = Validators.getOptionalField("notes", json)

                val meta = (
                    user_username,
                    notes
                ).mapN(RecordRequest)

                val data = (
                    username,
                    password,
                    meta
                ).mapN(UserRequest)

                res <- data match {
                    case Invalid(e) => BadRequest(e)
                    case Valid(x) => {
                        try {
                            val user = User(
                                id = 0,
                                record_id = 0,
                                staff_id = 1,
                                username = x.username,
                                password = x.password
                            )
                            val user_username = x.meta.user_username
                            val notes = x.meta.notes
                            Created(
                                UsersServices
                                    .create(user, user_username, notes)
                                    .transact(xa).unsafeRunSync
                            )
                        } catch {
                            case e: SQLException => {
                                BadRequest(Validators.sqlException(e))
                            }
                        }
                    }
                }
            } yield (res)
        }

        case PATCH -> Root => {
            Ok(UsersServices.edit(
                User(
                    id = 2,
                    record_id = 8,
                    staff_id = 1,
                    username = "un9999",
                    password = "pw9999"
                ),
                user_username = System.getenv("SUPER_USERNAME"),
                notes = Some("Test of updating notes.")
            ).transact(xa).unsafeRunSync)
        }

        case DELETE -> Root / username / action => { // TODO: move action into the body (and also add user_username into it as well)
            action match {
                case "soft" => {
                    NoContent(UsersServices.delete(
                        username,
                        user_username = System.getenv("SUPER_USERNAME")
                    ).transact(xa).unsafeRunSync)
                }
                case "restore" => {
                    NoContent(UsersServices.restore(
                        username,
                        user_username = System.getenv("SUPER_USERNAME")
                    ).transact(xa).unsafeRunSync)
                }
                case "hard" => {
                    NoContent(UsersServices.permanentlyDelete(username)
                        .transact(xa).unsafeRunSync)
                }
        // TODO: case other => BadRequest
            }
        }
    }
}
