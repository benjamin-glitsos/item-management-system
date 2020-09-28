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

       case GET -> Root / username => {
            // TODO: add user_username to the body
            Ok(UsersServices.open(username, System.getenv("SUPER_USERNAME")).transact(xa).unsafeRunSync)
        }

        case body @ POST -> Root => {
            for {
                json <- body.as[Json]

                // TODO: nest the body of all requests into? (Even if they don't have any data, then still nest everything under head)
                // {
                //     params: {},
                //     data: {}
                // }
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
                ).mapN(UserCreateBody)

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

        case body @ DELETE -> Root => {
            // TODO: should accept list of usernames then bulk delete them
            // TODO: will need try catch for db errors like all endpoints
            // TODO: rename 'action' to 'method'
            // TODO: use NonEmptyChain for validation instead of NonEmptyList
            // TODO: use XML XSD or Parambulator or JSON Schema?
            // TODO: accept XML data, but have option to return data as JSON. Then always use that option from the front-end but use XML for many of the tests so that you can validate it against schemas. You may have XSD testing schemas.
            // TODO: use a Java DOM XML parser
            // TODO: add to readme: XML, XSD, contract-based API, Java interop. Add to layers: contract layer
            // TODO: the XML will contain default values. Then it will return the XML tree-based data. Then you just directly get values from that tree. And you can use optional accessors for optional values and these will return Some/None. Therefore, all of this will now be handled by XSD.
            // TODO: instead of 'field' in Error case class, use mandatory 'id' String (don't use Option). Then use same id attribute on form fields on front-end. And these id attributes are determined in the XSD (by using id attribute)
            // TODO: xml format:
            // <message>
            //     <head></head>
            //     <body></body>
            // </message>
            for {
                json <- body.as[Json] // TODO: xml <- body map { parseXml(_) }

                val action = Validators.getRequiredField("action", json).andThen { action_ =>
                    Validators.isDeleteActionSupported(x.action)
                }
                // TODO: instead of getRequiredField function, try just using an optic and then using a function on the resulting Option. E.g. required and default functions just take an Option and return a Validated
                val user_username = Validators.getRequiredField("user_username", json)
                val username = Validators.getRequiredField("username", json)
                }

                val data = (
                    username,
                    action,
                    user_username
                ).mapN(UserDeleteBody)

                res <- data match {
                    case Invalid(e) => BadRequest(e)
                    case Valid(x) => {
                        val username = Validators.isUserDeletingThemselves(x.username, x.user_username)

                        x.action match {
                            case "soft" => {
                                Ok(UsersServices.delete(
                                    username,
                                    System.getenv("SUPER_USERNAME")
                                ).transact(xa).unsafeRunSync)
                            }
                            case "restore" => {
                                Ok(UsersServices.restore(
                                    username,
                                    System.getenv("SUPER_USERNAME")
                                ).transact(xa).unsafeRunSync)
                            }
                            case "hard" => {
                                Ok(UsersServices.permanentlyDelete(username).transact(xa).unsafeRunSync)
                            }
                            case other => BadRequest(other)
                        }
                    }
                }
            } yield (res)
            // import io.circe.optics.JsonPath._
            // val username = root.username.getOption(json) match {
            //     case Some(x) => x.validNel
            //     case None => {
            //         val code = "REQUIRED_FIELD_NOT_PROVIDED"
            //         val message = s"The required field 'username' was not provided."
            //         val field = "username"
            //         Error(code, message, field).invalidNel
            //     }
            // }
        }
    }
}
