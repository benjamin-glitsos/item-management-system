import java.time.LocalDateTime
import cats.effect._
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import io.circe.syntax._
import io.circe.Json
import org.http4s.circe._
import org.http4s.circe.CirceEntityEncoder._
import io.circe.generic.auto._
import io.circe.optics.JsonPath._
import doobie.implicits._
import bundles.doobie.connection._
import bundles.http4s._
import cats.data.Validated.{Invalid, Valid}

import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

import java.io.IOException
import java.sql.SQLException

import org.everit.json.schema.Schema
import org.everit.json.schema.loader.SchemaLoader
import org.json.JSONObject
import org.json.JSONTokener

object UsersRoutes extends ValidationUtilities {
    val endpoints = HttpRoutes.of[IO] {
        case req @ POST -> Root / "list" => {
            // try (InputStream inputStream = getClass().getResourceAsStream("/path/to/your/schema.json")) {
            //     JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            //     Schema schema = SchemaLoader.load(rawSchema);
            //     schema.validate(new JSONObject("{\"hello\" : \"world\"}")); // throws a ValidationException if this object is invalid
            // }
            for {
                body <- req.as[Json]
                res <- handleResponse(
                        UsersServices.list(
                            root.page_number.int.getOption(body).get,
                            root.page_length.int.getOption(body).get
                        ).transact(xa).unsafeRunSync
                    )
            } yield (res)
        }

        case _ => NotFound(Errors.resourceNotFound()).unsafeRunSync()

       // case GET -> Root / username => {
       //      Ok(UsersServices.open(username, System.getenv("SUPER_ADMIN_USERNAME")).transact(xa).unsafeRunSync)
       //  }

        // case body @ POST -> Root => {
        //     for {
        //         json <- body.as[Json]
        //
        //         val username = Validators.getRequiredField("username", json)
        //         val password = Validators.getRequiredField("password", json)
        //         val user_username = Validators.getRequiredField("user_username", json)
        //         val notes = Validators.getOptionalField("notes", json)
        //
        //         val meta = (
        //             user_username,
        //             notes
        //         ).mapN(RecordRequest)
        //
        //         val data = (
        //             username,
        //             password,
        //             meta
        //         ).mapN(UserCreateBody)
        //
        //         res <- data match {
        //             case Invalid(e) => BadRequest(e)
        //             case Valid(x) => {
        //                 try {
        //                     val user = User(
        //                         id = 0,
        //                         record_id = 0,
        //                         staff_id = 1,
        //                         username = x.username,
        //                         password = x.password
        //                     )
        //                     val user_username = x.meta.user_username
        //                     val notes = x.meta.notes
        //                     Created(
        //                         UsersServices
        //                             .create(user, user_username, notes)
        //                             .transact(xa).unsafeRunSync
        //                     )
        //                 } catch {
        //                     case e: SQLException => {
        //                         BadRequest(Validators.sqlException(e))
        //                     }
        //                 }
        //             }
        //         }
        //     } yield (res)
        // }

        // case PATCH -> Root => {
        //     Ok(UsersServices.edit(
        //         User(
        //             id = 2,
        //             record_id = 8,
        //             staff_id = 1,
        //             username = "un9999",
        //             password = "pw9999"
        //         ),
        //         user_username = System.getenv("SUPER_ADMIN_USERNAME"),
        //         notes = Some("Test of updating notes.")
        //     ).transact(xa).unsafeRunSync)
        // }

        // case body @ DELETE -> Root => {
        //     for {
        //         json <- body.as[Json]
        //
        //         val action = Validators.getRequiredField("action", json).andThen { action_ =>
        //             Validators.isDeleteActionSupported(x.action)
        //         }
        //         val user_username = Validators.getRequiredField("user_username", json)
        //         val username = Validators.getRequiredField("username", json)
        //         }
        //
        //         val data = (
        //             username,
        //             action,
        //             user_username
        //         ).mapN(UserDeleteBody)
        //
        //         res <- data match {
        //             case Invalid(e) => BadRequest(e)
        //             case Valid(x) => {
        //                 val username = Validators.isUserDeletingThemselves(x.username, x.user_username)
        //
        //                 x.action match {
        //                     case "soft" => {
        //                         Ok(UsersServices.delete(
        //                             username,
        //                             System.getenv("SUPER_ADMIN_USERNAME")
        //                         ).transact(xa).unsafeRunSync)
        //                     }
        //                     case "restore" => {
        //                         Ok(UsersServices.restore(
        //                             username,
        //                             System.getenv("SUPER_ADMIN_USERNAME")
        //                         ).transact(xa).unsafeRunSync)
        //                     }
        //                     case "hard" => {
        //                         Ok(UsersServices.permanentlyDelete(username).transact(xa).unsafeRunSync)
        //                     }
        //                     case other => BadRequest(other)
        //                 }
        //             }
        //         }
        //     } yield (res)
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
        // }
    }
}
