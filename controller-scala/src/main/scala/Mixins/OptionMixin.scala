trait OptionMixin {
  final def maybeEmpty(s: String): Option[String] = {
    Option(s).collect { case s if s.trim.nonEmpty => s }
  }
}
