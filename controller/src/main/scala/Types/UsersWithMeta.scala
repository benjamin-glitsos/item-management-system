import java.time.LocalDateTime

case class UsersWithMeta(
    email_address: String,
    username: String,
    password: String,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime],
    deleted_at: Option[LocalDateTime]
)
