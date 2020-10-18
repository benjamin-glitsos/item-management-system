import java.time.LocalDateTime

case class UsersList(
    email_address: Option[String],
    username: Option[String],
    password: Option[String],
    created_at: Option[LocalDateTime],
    created_by: Option[String],
    opened_at: Option[LocalDateTime],
    opened_by: Option[String],
    edited_at: Option[LocalDateTime],
    edited_by: Option[String],
    is_deleted: Option[Boolean]
)
