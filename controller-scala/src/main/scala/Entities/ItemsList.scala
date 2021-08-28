import java.util.Date

case class ItemsList(
    sku: String,
    name: String,
    description: Option[String],
    acquisition_date: Date,
    created_at: Int,
    edited_at: Option[Int]
)
