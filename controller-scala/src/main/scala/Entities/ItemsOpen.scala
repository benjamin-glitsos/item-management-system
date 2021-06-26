import java.util.Date

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
    created_at: String,
    created_by: String,
    edited_at: Option[String],
    edited_by: Option[String],
    deleted_at: Option[String],
    deleted_by: Option[String]
)
