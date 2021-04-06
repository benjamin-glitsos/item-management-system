import java.time.LocalDateTime

case class ItemsList(
    key: String,
    name: String,
    description: String,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime]
)
