import java.util.UUID

case class Users(
    id: Int,
    uuid: UUID,
    person_id: Int,
    username: String,
    password: String
)
