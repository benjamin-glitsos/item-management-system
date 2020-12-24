import java.time.LocalDateTime
import java.util.UUID

case class UsersWithMeta(
    username: String,
    password: String,
    email_address: String,
    uuid: UUID,
    edits: Int,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime],
    deleted_at: Option[LocalDateTime],
    restored_at: Option[LocalDateTime],
    notes: String
)
