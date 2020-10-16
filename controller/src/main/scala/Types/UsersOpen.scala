import java.time.LocalDateTime

case class UsersOpen(
    email_address: Option[String],
    username: Option[String],
    password: Option[String],
    created_at: Option[LocalDateTime],
    created_by: Option[String],
    opens: Option[Int],
    opened_at: Option[LocalDateTime],
    opened_by: Option[String],
    edits: Option[Int],
    edited_at: Option[LocalDateTime],
    edited_by: Option[String],
    deleted_at: Option[LocalDateTime],
    deleted_by: Option[String],
    restored_at: Option[LocalDateTime],
    restored_by: Option[String],
    notes: Option[String]
)
