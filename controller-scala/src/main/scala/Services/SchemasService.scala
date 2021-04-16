object SchemasService {
  final def get(name: String) = SchemasDAO.get(name: String)
}
