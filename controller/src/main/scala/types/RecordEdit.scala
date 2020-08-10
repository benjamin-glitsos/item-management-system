import java.util.UUID
import java.time.LocalDateTime

case class RecordEdit(
    uuid: UUID,
    time: LocalDateTime,
    user_id: Int
)
