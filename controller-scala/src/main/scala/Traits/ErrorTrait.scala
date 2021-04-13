import org.typelevel.ci.{CIString => CaseInsensitive}

trait ErrorTrait {
  final val NOT_FOUND            = CaseInsensitive("not_found")
  final val INVALID_INPUT        = CaseInsensitive("invalid_input")
  final val AUTHORISATION_FAILED = CaseInsensitive("authorisation_failed")
}
