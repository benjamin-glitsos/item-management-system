import java.time.LocalDateTime

case class UsersWithMeta(
    username: String,
    password: String,
    email_address: String,
    metakey: String,
    opens: Int,
    edits: Int,
    is_deleted: Boolean,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime],
    deleted_at: Option[LocalDateTime],
    restored_at: Option[LocalDateTime],
    notes: String
)
