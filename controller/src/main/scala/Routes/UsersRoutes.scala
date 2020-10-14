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
    // use kompose for converting docker compose to kubernetes
    // TODO: use JSON Schema within Controller and later create an API that sends it to the Portal to be converted into OpenAPI format and combined with the OpenAPI YAML then used in an Angular component of the Swagger UI.
    // TODO: try using built-in auth middleware. Middleware will be: https, auto-slash, auth. But later you will probably make your own auth middleware so that you can add the user roles to the JWT and then also check access control within the same middleware.
    // TODO: try circe/circe-json-schema returning JSON with defaults within Valid() otherwise use everit-org/json-schema. But circe-json-schema is so small you can just copy most of the code
    // TODO: Controller folder structure:
    // src/main/scala/
    // src/test/scala/
    // TODO: use apply method for objects like Routers and Seeders so that they can be used like Routers()
    // TODO: use kompose for converting docker compose to kubernetes
    // TODO: add object id to Meta table. Then make a Meta.redirect Service that will accept a UUID and return the front-end URL to redirect to. e.g. /{uuid} -> /api/redirect ({uuid}) -> { table_name: {object name}, business_key: {business key} }
    // TODO: rename Records to Meta
    // TODO: use .gitattributes to add SQL to the language statistics. Remove CSS?
    // TODO: use NonEmptyChain for validation instead of NonEmptyList
    // TODO: the XML will contain default values. Then it will return the XML tree-based data. Then you just directly get values from that tree. And you can use optional accessors for optional values and these will return Some/None. Therefore, all of this will now be handled by XSD.
    // TODO: instead of 'field' in Error case class, use mandatory 'id' String (don't use Option). Then use same id attribute on form fields on front-end. And these id attributes are determined in the XSD (by using id attribute)
    // TODO: Add 'level' to Error. type Level = High | Medium | Low
    // TODO: work by changing Staff DAO, services, seeder, etc. to Equipment. Just change fields and naming mainly
    // TODO: Have two users that are created at startup: Super Admin (super_admin role) & Guest Admin (admin role)
    // TODO: Use database Views plus JSON to merge commonly-merged tables into one and then just use the Quill/Doobie to work with these Views. Multiple columns are merged into one JSON column. Have a new views.sql file for this. These Views will be:
    // * users_with_roles
    // * transactions_with_type
    // * records_with_users (json fields: username, avatar)
    // TODO: casbin error message will always be the same: "access_denied", "You do not have permission to '$action' this '$object' resource at this time."
    // TODO: casbin model will be ABAC with roles and superuser role
    // TODO: use openapi4j and swagger ui for contracts
    // TODO: request and response format:
    // {
    //     head: {},
    //     body: {}
    // }
    // TODO: add additional information to the json schemas and then use that to generate the front-end forms automatically? Have an api parameter that requests the schema of an endpoint and it will return properties like description, bootstrap-column
    // TODO: make logging middleware that prints to console for now. A good simple middleware to create firstly
    // TODO: after OAS validation, you can just use optics to get values directly out of Circe JSON. don't even use maybe. it's already within validated data type so already typesafe
    // TODO: middleware after request will check response against the openapi, but only if testing parameter is set to true
    // TODO: hash passwords asyncronously (use ZIO)
    // TODO: have a centre-aligned menu for front-end. Don't use a sidebar menu.
    // TODO: new routing pattern:
    // GET /api/options (Returns an array of the available actions that this user has access to perform. This call is made when the page first loads.)
    // GET /api/{object}/{action}
    // Where action:
    //     open
    //     save
    //     create
    //     delete (restore is a parameter)
    //     hard delete
    //     buy
    //     sell
    //     options (Returns an array of all of the objects from that table in the database. (Add an attribute to filter by only those in the menu (have a table field for this) and then use this API to map the menu items. Make sure to add this header: Allow: POST)
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
        case req @ POST -> Root / "list" => {
            for {
                json <- req.as[Json]
                res <- () => {
                    try {
                        Ok(
                            UsersServices.list(
                                json.body.page_number, json.body.page_length
                            ).transact(xa).unsafeRunSync
                        )
                    } catch {
                        case err: SQLException => {
                            BadRequest(Validators.sqlException(err))
                        }
                    }
                }
            } yield (res)
        }

       // case GET -> Root / username => {
       //      // TODO: add user_username to the body
       //
       //      Ok(UsersServices.open(username, System.getenv("SUPER_ADMIN_USERNAME")).transact(xa).unsafeRunSync)
       //  }

        // case body @ POST -> Root => {
        //     for {
        //         json <- body.as[Json]
        //
        //         // TODO: nest the body of all requests into? (Even if they don't have any data, then still nest everything under head)
        //         // {
        //         //     params: {},
        //         //     data: {}
        //         // }
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
        //     // TODO: for accessing the nested data, you'll need to make Validators.getRequiredField take a list of strings which will act as a path to the JSON data. And then it will join it with dots to pass to the error message e.g. data.notes. TODO: actually, should you use 'optics' for accessing this considering there are nested paths?
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
        //     // TODO: will need try catch for db errors like all endpoints
        //     // TODO: rename 'action' to 'method'
        //     for {
        //         json <- body.as[Json] // TODO: xml <- body map { validateXML(parseXML(_)) }
        //
        //         val action = Validators.getRequiredField("action", json).andThen { action_ =>
        //             Validators.isDeleteActionSupported(x.action)
        //         }
        //         // TODO: instead of getRequiredField function, try just using an optic and then using a function on the resulting Option. E.g. required and default functions just take an Option and return a Validated
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
