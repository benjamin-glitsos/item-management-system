case class ItemsOpen(
    sku: String,
    name: String,
    description: Option[String],
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
