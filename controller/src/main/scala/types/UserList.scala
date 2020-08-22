import java.time.LocalDateTime

case class UserList(
    username: String,
    edited_at: Option[LocalDateTime],
    edited_by: Option[String],
    created_at: LocalDateTime,
    created_by: String
)
