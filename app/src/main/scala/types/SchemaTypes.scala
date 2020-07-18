import java.sql.Timestamp

object SchemaTypes {
    case class Record(
        id: Int,
        created_at: Option[Timestamp],
        created_by: Option[Int],
        updated_at: Option[Timestamp],
        updated_by: Option[Int],
        deleted_at: Option[Timestamp],
        deleted_by: Option[Int]
    )

    type Person = (
        Int,
        Int,
        String,
        String,
        Option[String],
        Int,
        String,
        String,
        String,
        String,
        String
    )

    type User = (Int, Int, String, String)

    case class Sex(id: Int, name: String)
}
