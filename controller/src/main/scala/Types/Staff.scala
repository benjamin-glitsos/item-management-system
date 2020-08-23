import java.util.Date

case class Staff(
    id: Int,
    record_id: Int,
    person_id: Int,
    staff_number: String,
    employment_start: Date,
    employment_end: Date
)
