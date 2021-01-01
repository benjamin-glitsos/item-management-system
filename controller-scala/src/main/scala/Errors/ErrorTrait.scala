import org.typelevel.ci.{CIString => CaseInsensitiveString}

trait ErrorTrait {
  final def error(code: String, message: String): Error = {
    Error(CaseInsensitiveString(code), message)
  }
}
