import java.util.UUID
import java.time.LocalDateTime

case class Record(
    id: Int,
    uuid: UUID,
    created_at: LocalDateTime,
    created_by: Int,
    updated_at: Option[LocalDateTime],
    updated_by: Option[Int],
    deleted_at: Option[LocalDateTime],
    deleted_by: Option[Int]
)
