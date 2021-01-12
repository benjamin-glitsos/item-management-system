import org.typelevel.ci.{CIString => CaseInsensitive}

case class Error(
    code: CaseInsensitive,
    title: String,
    description: String
)
