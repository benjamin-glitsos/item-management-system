import java.util.UUID
import java.time.LocalDateTime

case class Meta(
    id: Int,
    uuid: UUID,
    created_at: LocalDateTime,
    created_by: Int,
    opens: Int,
    opened_at: Option[LocalDateTime],
    opened_by: Option[Int],
    edits: Int,
    edited_at: Option[LocalDateTime],
    edited_by: Option[Int],
    deleted_at: Option[LocalDateTime],
    deleted_by: Option[Int],
    restored_at: Option[LocalDateTime],
    restored_by: Option[Int],
    notes: Option[String]
)
