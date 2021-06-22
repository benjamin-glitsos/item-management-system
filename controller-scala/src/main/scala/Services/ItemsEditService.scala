import java.sql.SQLException
import doobie.implicits._
import doobie_import.connection._
import upickle.default._
import upickle_import.general._

trait ItemsEditService extends ServiceTrait {
  final def edit(
      oldSku: String,
      newSku: Option[String],
      name: Option[String],
      description: Option[Option[String]],
      additionalNotes: Option[Option[String]]
  ): ujson.Value = {
    read[ujson.Value](
      try {
        (for {
          _ <- ItemsDAO
            .edit(
              oldSku,
              newSku,
              name,
              description,
              additionalNotes
            )

          data <- ItemsDAO.open(newSku.getOrElse(oldSku))

          val output: String = createDataOutput(writeJs(data))

        } yield (output))
          .transact(transactor)
          .unsafeRunSync
      } catch {
        case e: SQLException => handleSqlException(e)
      }
    )
  }
}
