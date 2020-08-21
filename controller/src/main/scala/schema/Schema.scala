import io.getquill.{ idiom => _, _ }
import doobie.quill.DoobieContext

object Schema {
    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    val records = quote {
        querySchema[Record]("records")
    }

    val users = quote {
        querySchema[User]("users")
    }
}
