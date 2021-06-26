import java.util.Date
import java.time.LocalDateTime

case class ItemsList(
    sku: String,
    name: String,
    description: Option[String],
    acquisition_date: Date,
    created_at: LocalDateTime,
    edited_at: Option[LocalDateTime]
)
