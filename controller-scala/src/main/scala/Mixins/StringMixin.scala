trait StringMixin {
  final def isEmpty(s: String): Boolean = s == null || s.isEmpty

  final def toTitleCase(s: String): String =
    s.split(" ").map(_.capitalize).mkString(" ")

  final def doubleQuote(s: String): String = "\"" + s + "\""

  final def inline(s: String): String = "[\\n\\r]\\s+".r.replaceAllIn(s, "")
}
