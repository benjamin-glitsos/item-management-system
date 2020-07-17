import java.sql.Timestamp

object SchemaTypes {
    type Record = (
        Int,
        Option[Timestamp],
        Option[Int],
        Option[Timestamp],
        Option[Int],
        Option[Timestamp],
        Option[Int]
    )

    type Person = (
        Int,
        Int,
        String,
        String,
        Option[String],
        String,
        String,
        String,
        Option[String],
        String,
        String
    )

    type User = (Int, Int, String, String)
}
