import java.time.LocalDateTime
import java.util.UUID

case class UsersWithMeta(
    email_address: String,
    username: String,
    password: String,
    uuid: UUID,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime],
    deleted_at: Option[LocalDateTime],
    restored_at: Option[LocalDateTime],
    notes: String
)
