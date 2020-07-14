import java.sql.Timestamp

object Types {
    type Record = (Int, Timestamp, Int, Timestamp, Int, Timestamp, Int)

    type User = (Int, Int, String, String)
}
