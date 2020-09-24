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
        case GET -> Root :? MaybeNumber(maybeNumber) +& MaybeLength(maybeLength) => {
            // TODO: move these query params into the body
            // TODO: return total length (count) of list
            // TODO: first query will tell angular the total count of list. Then angular calculates the allowable page length and page number, and this is verified by the validation by this route
            // TODO: return all data in Json response:
            // meta: {
            //     total_length: Int,
            //     page_length: Int,
            //     page: Int,
            //     total_pages: Int,
            //     range_start: Int,
            //     range_end: Int,
            // },
            // data: Json
            PartialContent(UsersServices.list(maybeNumber, maybeLength).transact(xa).unsafeRunSync)
        }

        // case GET -> Root / username => {
        //     // TODO: add user_username to the body
        //     UsersServices.open(username).transact(xa).unsafeRunSync match {
        //         case Invalid(e) => NotFound(e)
        //         case Valid(v) => Ok(v)
        //     }
        // }

        case body @ POST -> Root => {
            for {
                body_ <- body.as[Json]

                val username = Validators.getRequiredField("username", body_)
                val password = Validators.getRequiredField("password", body_)
                val user_username = Validators.getRequiredField("user_username", body_)
                val notes = Validators.getOptionalField("notes", body_)

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
            // TODO: the body will be like:
            // {
            //     username: String,
            //     data: {
            //         user: User,
            //         notes: String
            //     },
            //     user_username: String
            // }
            // TODO: for accessing the nested data, you'll need to make Validators.getRequiredField take a list of strings which will act as a path to the JSON data. And then it will join it with dots to pass to the error message e.g. data.notes. TODO: actually, should you use 'optics' for accessing this considering there are nested paths?
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

        case DELETE -> Root / username / action => {
            // TODO: error - user cannot delete themselves
            // TODO: move everything into the body
            // TODO: should accept list of usernames then bulk delete them
            action match {
                case "soft" => {
                    Ok(UsersServices.delete(
                        username,
                        user_username = System.getenv("SUPER_USERNAME")
                    ).transact(xa).unsafeRunSync)
                }
                case "restore" => {
                    Ok(UsersServices.restore(
                        username,
                        user_username = System.getenv("SUPER_USERNAME")
                    ).transact(xa).unsafeRunSync)
                }
                case "hard" => {
                    Ok(UsersServices.permanentlyDelete(username)
                        .transact(xa).unsafeRunSync)
                }
        // TODO: case other => BadRequest
            }
        }
    }
}
