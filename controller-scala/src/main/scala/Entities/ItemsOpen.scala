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
    created_at: Int,
    created_by: String,
    edited_at: Option[Int],
    edited_by: Option[String],
    deleted_at: Option[Int],
    deleted_by: Option[String]
)
