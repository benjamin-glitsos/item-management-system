case class UsersOpen(
    username: String,
    email_address: String,
    first_name: String,
    last_name: String,
    other_names: Option[String],
    password: String,
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
