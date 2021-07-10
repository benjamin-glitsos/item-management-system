import cats.data.NonEmptyChain
import upickle.default._

object ErrorsUtilities extends UpickleTrait {
  final def serialiseErrors(errors: NonEmptyChain[Error]): String = {
    write(
      errors.toChain.toList
    )
  }
}
