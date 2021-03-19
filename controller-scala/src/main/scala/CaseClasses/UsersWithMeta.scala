import java.time.LocalDateTime

case class UsersWithMeta(
    username: String,
    email_address: String,
    first_name: String,
    last_name: String,
    other_names: Option[String],
    password: String,
    metakey: String,
    opens: Int,
    edits: Int,
    is_deleted: Boolean,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime],
    deleted_at: Option[LocalDateTime],
    notes: Option[String]
)
