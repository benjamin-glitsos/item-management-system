import io.getquill.{ idiom => _, _ }
import doobie.quill.DoobieContext

object Schema {
    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    val records = quote {
        querySchema[Record](
            // sys.env.get("RECORDS_TABLE").orElse("records")
            "records"
        )
        querySchema[User](
            // sys.env.get("USERS_TABLE").orElse("users")
            "users"
        )
    }
}
