import java.time.LocalDateTime

case class UsersList(
    username: String,
    email_address: String,
    first_name: String,
    last_name: String,
    other_names: Option[String],
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime]
)
