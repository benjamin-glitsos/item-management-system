import java.time.LocalDateTime

case class MetaListView(
    id: Int,
    created_at: LocalDateTime,
    created_by: String,
    opened_at: Option[LocalDateTime],
    opened_by: String,
    edited_at: Option[LocalDateTime],
    edited_by: String,
    deleted_at: Option[LocalDateTime],
    notes: Option[String]
)
