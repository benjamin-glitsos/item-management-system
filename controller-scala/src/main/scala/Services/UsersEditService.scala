import java.sql.SQLException
import doobie.implicits._
import upickle.default._

trait UsersEditService
    extends ServiceMixin
    with DoobieConnectionMixin
    with UpickleMixin {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      firstName: Option[String],
      lastName: Option[String],
      otherNames: Option[Option[String]],
      emailAddress: Option[String],
      password: Option[String],
      additionalNotes: Option[Option[String]]
  ): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          data <- UsersDAO
            .edit(
              oldUsername,
              newUsername,
              firstName,
              lastName,
              otherNames,
              emailAddress,
              password,
              additionalNotes
            )

          val output: String = createDataOutput(writeJs(data))

        } yield (output)).transact(transactor).unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
