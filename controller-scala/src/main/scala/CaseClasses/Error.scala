import org.typelevel.ci.{CIString => CaseInsensitive}

case class Error(
    code: CaseInsensitive,
    message: String
)
