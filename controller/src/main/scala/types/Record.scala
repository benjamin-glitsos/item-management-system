import java.util.UUID
import java.time.LocalDateTime

case class Record(
    id: Int,
    uuid: UUID,
    created_at: LocalDateTime,
    created_by: Int,
    edits: Int,
    edited_at: Option[LocalDateTime],
    edited_by: Option[Int],
    deleted_at: Option[LocalDateTime],
    deleted_by: Option[Int],
    notes: Option[String]
)
