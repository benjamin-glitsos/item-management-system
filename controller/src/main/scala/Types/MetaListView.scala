import java.time.LocalDateTime

case class MetaListView(
    id: Option[Int],
    created_at: Option[ LocalDateTime ],
    created_by: Option[String],
    opened_at: Option[LocalDateTime],
    opened_by: Option[String],
    edited_at: Option[LocalDateTime],
    edited_by: Option[String],
    deleted_at: Option[LocalDateTime],
    notes: Option[String]
)
