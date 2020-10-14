import java.time.LocalDateTime

case class MetaListView(
    id: Int,
    created_at: LocalDateTime,
    created_by: String,
    opens: Int,
    opened_at: Option[LocalDateTime],
    opened_by: String,
    edits: Int,
    edited_at: Option[LocalDateTime],
    edited_by: String,
    deleted_at: Option[LocalDateTime],
    deleted_by: String,
    restored_at: Option[LocalDateTime],
    restored_by: String,
    notes: Option[String]
)
