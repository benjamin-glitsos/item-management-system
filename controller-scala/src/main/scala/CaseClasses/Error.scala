import org.typelevel.ci.{CIString => CaseInsensitiveString}

case class Error(
    code: CaseInsensitiveString,
    message: String
)
