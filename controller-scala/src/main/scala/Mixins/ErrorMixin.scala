import cats.data.NonEmptyChain
import upickle.default._
import org.apache.commons.lang3.exception.ExceptionUtils._

trait ErrorMixin extends UpickleMixin with StringMixin {
  final def necToJson(errors: NonEmptyChain[Error]): ujson.Value = {
    write(
      errors.toChain.toList
    )
  }
}
