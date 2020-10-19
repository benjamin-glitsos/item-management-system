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
    // TODO: TRY AKKA HTTP
    // TODO: delete endpoint will take a "method" of soft, hard or restore.
    // TODO: delete endpoint will take a list of UUIDs. The users delete service will actually be a passthrough that calls the meta delete service which actually contains the functionality.
    // TODO: create psql trigger to encrypt password using env file password as key. Create new file: triggers.sql or functions.sql?
    // TODO: use kompose for converting docker compose to kubernetes
    // TODO: use JSON Schema within Controller and later create an API that sends it to the Portal to be converted into OpenAPI format and combined with the OpenAPI YAML then used in an Angular component of the Swagger UI.
    // TODO: try using built-in auth middleware. Middleware will be: https, auto-slash, auth. But later you will probably make your own auth middleware so that you can add the user roles to the JWT and then also check access control within the same middleware.
    // TODO: try circe/circe-json-schema returning JSON with defaults within Valid() otherwise use everit-org/json-schema. But circe-json-schema is so small you can just copy most of the code
    // TODO: Controller folder structure:
    // src/main/scala/
    // src/test/scala/
    // TODO: use apply method for objects like Routers and Seeders so that they can be used like Routers()
    // TODO: use kompose for converting docker compose to kubernetes
    // TODO: the XML will contain default values. Then it will return the XML tree-based data. Then you just directly get values from that tree. And you can use optional accessors for optional values and these will return Some/None. Therefore, all of this will now be handled by XSD.
    // TODO: instead of 'field' in Error case class, use mandatory 'id' String (don't use Option). Then use same id attribute on form fields on front-end. And these id attributes are determined in the XSD (by using id attribute)
    // TODO: Use database Views plus JSON to merge commonly-merged tables into one and then just use the Quill/Doobie to work with these Views. Multiple columns are merged into one JSON column. Have a new views.sql file for this. These Views will be:
    // * users_with_roles
    // * transactions_with_type
    // * records_with_users (json fields: username, avatar)
    // TODO: casbin error message will always be the same: "access_denied", "You do not have permission to '$action' this '$object' resource at this time."
    // TODO: casbin model will be ABAC with roles and superuser role
    // TODO: add additional information to the json schemas and then use that to generate the front-end forms automatically? Have an api parameter that requests the schema of an endpoint and it will return properties like description, bootstrap-column
    // TODO: make logging middleware that prints to console for now. A good simple middleware to create firstly
    // TODO: hash passwords asyncronously (use ZIO)
    // Overall routing structure is:
    // / => Front-end
    // /api/ => API
    // /dev/ => dev portal (serve a plain HTML page which lists the dev pages. you will create a 'views' folder)
    // /dev/adminer/
    // /dev/api-docs/
    // /dev/db-docs/
    // TODO: create routing files:
    // RootRoutes.scala
    // DevRoutes.scala
    // ApiRoutes.scala
    // TODO: make two functions for casbin: one to validate a request, and the other to validate a list of actions to return only the ones that can be accessed. This will map casbin over the list of actions but keep the other parameters the same between each step in the map
    // TODO: add a cache control middleware to disable all caching
    // TODO: add server redirect middleware http to https
    // TODO: add error formatting middleware that groups by id. But have an optional parameter to turn this off.
    // TODO: make a middleware folder
    // TODO: remove NGINX from angular container

    val endpoints = HttpRoutes.of[IO] {
        case req @ POST -> Root / "list" => {
            // try (InputStream inputStream = getClass().getResourceAsStream("/path/to/your/schema.json")) {
            //     JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            //     Schema schema = SchemaLoader.load(rawSchema);
            //     schema.validate(new JSONObject("{\"hello\" : \"world\"}")); // throws a ValidationException if this object is invalid
            // }
            for {
                body <- req.as[Json] map { jsonSchema(_) } // TODO: Json Schema
                // TODO: maybe do something like: jsonSchema(body) *> handleResponse.
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
       //      // TODO: add user_username to the body
       //
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
        //     // TODO: the body will be like:
        //     // {
        //     //     username: String,
        //     //     data: {
        //     //         user: User,
        //     //         notes: String
        //     //     },
        //     //     user_username: String
        //     // }
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
        //         json <- body.as[Json] // TODO: xml <- body map { validateXML(parseXML(_)) }
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
