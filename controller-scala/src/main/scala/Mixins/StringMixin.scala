object StringMixin {
  final def isEmpty(s: String): Boolean = s == null || s.isEmpty

  final def toTitleCase(s: String): String =
    s.split(" ").map(_.capitalize).mkString(" ")
}
