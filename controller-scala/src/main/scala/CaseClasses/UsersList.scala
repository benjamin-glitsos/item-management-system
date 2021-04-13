import java.time.LocalDateTime

case class UsersList(
    username: String,
    name: String,
    email_address: String,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime]
)
