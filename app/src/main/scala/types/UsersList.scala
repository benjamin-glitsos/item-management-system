import java.sql.Timestamp

case class UsersList(
    username: String,
    name: String,
    created_at: Option[Timestamp]
)
