import java.sql.Timestamp

case class RecordMetadata(
    created_at: Option[Timestamp],
    created_by_user_id: Int,
    created_by_user_first_name: String,
    created_by_user_last_name: String,
    created_by_user_other_names: Option[String],
    updated_at: Option[Timestamp],
    updated_by_user_id: Int,
    updated_by_user_first_name: String,
    updated_by_user_last_name: String,
    updated_by_user_other_names: Option[String],
    deleted_at: Option[Timestamp],
    deleted_by_user_id: Int,
    deleted_by_user_first_name: String,
    deleted_by_user_last_name: String
    deleted_by_user_other_names: Option[String],
)
