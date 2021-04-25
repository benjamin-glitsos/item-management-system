case class UsersOpen(
    username: String,
    email_address: String,
    first_name: String,
    last_name: String,
    other_names: Option[String],
    password: String,
    metakey: String,
    opens: Int,
    edits: Int,
    is_deleted: Boolean,
    created_at: String,
    edited_at: Option[String],
    deleted_at: Option[String],
    additional_notes: Option[String]
)
