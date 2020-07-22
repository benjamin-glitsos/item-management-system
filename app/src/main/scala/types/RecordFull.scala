import java.sql.Timestamp

case class RecordFull(
    created_at: Option[Timestamp],
    updated_at: Option[Timestamp],
    deleted_at: Option[Timestamp],
    created_by: User,
    updated_by: User,
    deleted_by: User
)
