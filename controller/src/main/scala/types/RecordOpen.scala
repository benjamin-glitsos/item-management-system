import java.util.UUID
import java.time.LocalDateTime

case class RecordOpen(
    uuid: UUID,
    created_at: LocalDateTime,
    created_by: String,
    views: Int,
    viewed_at: Option[LocalDateTime],
    viewed_by: Option[String],
    edits: Int,
    edited_at: Option[LocalDateTime],
    edited_by: Option[String],
    deletions: Int,
    deleted_at: Option[LocalDateTime],
    deleted_by: Option[String],
    restored_at: Option[LocalDateTime],
    restored_by: Option[String],
    notes: Option[String]
)
