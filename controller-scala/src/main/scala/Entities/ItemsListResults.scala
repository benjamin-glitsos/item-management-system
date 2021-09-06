import java.util.Date

case class ItemsListResults(
    totalCount: Int,
    filteredCount: Int,
    pageStart: Int,
    pageEnd: Int,
    sku: String,
    name: String,
    description: Option[String],
    acquisitionDate: Date,
    createdAt: Int,
    editedAt: Option[Int]
)
