import java.time.LocalDateTime
import java.util.UUID

case class UsersList(
    email_address: String,
    username: String,
    password: String,
    uuid: UUID,
    created_at: LocalDateTime,
    created_by: String,
    opened_at: Option[LocalDateTime],
    opened_by: Option[String],
    edited_at: Option[LocalDateTime],
    edited_by: Option[String],
    is_deleted: Boolean
)
