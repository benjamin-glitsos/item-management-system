import java.time.LocalDateTime

case class ItemsWithMeta(
    key: String,
    name: String,
    description: Option[String],
    metakey: String,
    opens: Int,
    edits: Int,
    is_deleted: Boolean,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime],
    deleted_at: Option[LocalDateTime],
    notes: Option[String]
)
