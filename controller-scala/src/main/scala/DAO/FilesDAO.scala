import scala.io.Source
import scala.util.Using

object FilesDAO {
  private final def open(path: String): String = (Using(
    Source.fromFile(s"/app/files/$path")
  )(_.mkString)).getOrElse(new String)

  final def openPublicFile(path: String): String  = open(s"public/$path")
  final def openPrivateFile(path: String): String = open(s"private/$path")
}
