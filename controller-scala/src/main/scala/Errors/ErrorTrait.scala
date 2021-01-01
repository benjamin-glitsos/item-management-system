import org.typelevel.ci.CIString

trait ErrorTrait {
  final def error(code: String, message: String): Error = {
    Error(CIString(code), message)
  }
}
