import java.sql.Timestamp

case class Record(
    id: Int,
    created_at: Option[Timestamp],
    created_by: Option[Int],
    updated_at: Option[Timestamp],
    updated_by: Option[Int],
    deleted_at: Option[Timestamp],
    deleted_by: Option[Int]
)
