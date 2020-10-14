import java.time.LocalDateTime

case class Meta(
    id: Int,
    created_at: LocalDateTime,
    created_by_id: Int,
    opens: Int,
    opened_at: Option[LocalDateTime],
    opened_by_id: Option[Int],
    edits: Int,
    edited_at: Option[LocalDateTime],
    edited_by_id: Option[Int],
    deleted_at: Option[LocalDateTime],
    deleted_by_id: Option[Int],
    restored_at: Option[LocalDateTime],
    restored_by_id: Option[Int],
    notes: Option[String]
)
