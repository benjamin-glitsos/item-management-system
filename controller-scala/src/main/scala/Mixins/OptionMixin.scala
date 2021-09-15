trait OptionMixin {
  final def maybeEmptyString(s: String): Option[String] = {
    Option(s).collect { case s if s.trim.nonEmpty => s }
  }
}
