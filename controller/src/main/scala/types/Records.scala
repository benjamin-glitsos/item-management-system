import java.sql.Timestamp

case class Records(
    id: Int,
    created_at: Timestamp,
    created_by: Int,
    updated_at: Timestamp,
    updated_by: Int,
    deleted_at: Timestamp,
    deleted_by: Int
)
