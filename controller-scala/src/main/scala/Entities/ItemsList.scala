import java.util.Date

case class ItemsList(
    sku: String,
    name: String,
    description: Option[String],
    acquisition_date: Date,
    created_at: String,
    edited_at: Option[String]
)
