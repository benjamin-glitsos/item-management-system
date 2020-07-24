import java.sql.Timestamp

case class UsersList(
    username: String,
    first_name: String,
    last_name: String,
    other_names: Option[String],
    created_at: Option[Timestamp]
)
