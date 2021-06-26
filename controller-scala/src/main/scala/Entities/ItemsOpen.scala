import java.util.Date
import java.time.LocalDateTime

case class ItemsOpen(
    sku: String,
    upc: String,
    name: String,
    description: Option[String],
    acquisition_date: Date,
    expiration_date: Option[Date],
    unit_cost: Double,
    unit_price: Option[Double],
    quantity_available: Int,
    quantity_sold: Int,
    additional_notes: Option[String],
    metakey: String,
    opens: Int,
    edits: Int,
    is_deleted: Boolean,
    created_at: LocalDateTime,
    created_by: String,
    edited_at: Option[LocalDateTime],
    edited_by: Option[String],
    deleted_at: Option[LocalDateTime],
    deleted_by: Option[String]
)
