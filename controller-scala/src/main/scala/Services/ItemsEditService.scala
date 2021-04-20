import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait ItemsEditService extends ServiceTrait {
  final def edit(
      oldKey: String,
      newKey: Option[String],
      name: Option[String],
      description: Option[Option[String]],
      additionalNotes: Option[Option[String]]
  ): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          _ <- ItemsDAO
            .edit(
              oldKey,
              newKey,
              name,
              description,
              additionalNotes
            )

          data <- ItemsDAO.open(newKey.getOrElse(oldKey))

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
