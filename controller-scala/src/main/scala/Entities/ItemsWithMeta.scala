case class ItemsWithMeta(
    key: String,
    name: String,
    description: Option[String],
    metakey: String,
    opens: Int,
    edits: Int,
    is_deleted: Boolean,
    created_at: String,
    edited_at: Option[String],
    deleted_at: Option[String],
    additional_notes: Option[String]
)
