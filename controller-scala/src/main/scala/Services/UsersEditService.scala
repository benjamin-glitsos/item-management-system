import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait UsersEditService extends ServiceTrait {
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
          _ <- UsersDAO
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

          data <- UsersDAO.open(newUsername.getOrElse(oldUsername))

          val output: String = createDataOutput(writeJs(data))

        } yield (output))
          .transact(xa)
          .unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
