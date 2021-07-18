import org.json.{JSONObject, JSONTokener}

object SchemasDAO {
  final def load(name: String): JSONObject = new JSONObject(
    new JSONTokener(FilesDAO.openPrivateFile(s"schemas/$name.json"))
  )
}
