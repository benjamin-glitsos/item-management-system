import java.sql.SQLException
import upickle.default._

trait ServiceMixin {
  def createDataOutput(data: ujson.Value): String = write(
    ujson.Obj(
      "data" -> data
    )
  )

  def handleSqlException(e: SQLException): String = {
    System.err.println(e.getMessage)
    System.err.println(e.getSQLState)
    new String
  }
}
