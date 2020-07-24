import java.sql.Timestamp

case class UsersList(
    username: String,
    first_name: String,
    last_name: String,
    created_at: Option[Timestamp],
    deleted_at: Option[Timestamp]
)
