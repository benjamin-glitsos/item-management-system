import java.time.LocalDateTime
import java.util.UUID

case class UsersOpen(
    email_address: String,
    username: String,
    uuid: UUID,
    created_at: LocalDateTime,
    created_by: String,
    opens: Int,
    opened_at: Option[LocalDateTime],
    opened_by: Option[String],
    edits: Int,
    edited_at: Option[LocalDateTime],
    edited_by: Option[String],
    deleted_at: Option[LocalDateTime],
    deleted_by: Option[String],
    restored_at: Option[LocalDateTime],
    restored_by: Option[String],
    is_deleted: Boolean,
    notes: Option[String]
)
