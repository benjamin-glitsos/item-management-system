import java.sql.Timestamp

case class RecordMetadata(
    created_at: Option[Timestamp],
    created_by: PersonName,
    updated_at: Option[Timestamp],
    updated_by: PersonName,
    deleted_at: Option[Timestamp],
    deleted_by: PersonName
)
