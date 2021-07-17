trait TupleMixin {
  def bifurcate(s: String, separator: String): (String, String) = {
    val Array(a, b) = s.split(separator)
    (a, b)
  }
}
