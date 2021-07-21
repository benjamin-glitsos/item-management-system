trait TupleMixin {
  def bifurcate(s: String): (String, String) = {
    var Array(a: String, b: String) = s.split('.')
    (a, b)
  }
}
