trait TupleMixin {
  def bifurcate(s: String, separator: String): (String, String) = {
    val Array(a: String, b: String) = s.split(separator)
    (a, b)
  }
}
