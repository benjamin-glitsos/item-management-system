import scala.io.Source
import scala.util.Using

object FilesDAO {
  final def open(path: String): String = (Using(
    Source.fromFile(s"/app/public/$path")
  )(_.mkString)).getOrElse(new String)
}
