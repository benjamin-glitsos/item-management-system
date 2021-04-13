import cats.data.NonEmptyChain
import upickle.default._
import upickle_import.general._

object ErrorsUtilities {
  final def serialiseErrors(errors: NonEmptyChain[Error]): String = {
    write(
      errors.toChain.toList
    )
  }
}
