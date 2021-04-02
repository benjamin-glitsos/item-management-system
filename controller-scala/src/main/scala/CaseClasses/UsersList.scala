import java.time.LocalDateTime

case class UsersList(
    username: String,
    email_address: String,
    name: String,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime]
)
