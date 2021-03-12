object OptionUtilities {
  final def emptyStringToOption(s: String): Option[String] = {
    Option(s).collect { case s if s.trim.nonEmpty => s }
  }
}
