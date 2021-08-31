import cats.data.NonEmptyChain
import upickle.default._
import org.apache.commons.lang3.exception.ExceptionUtils._

trait ErrorMixin extends UpickleMixin with StringMixin {
  final def necToJson(errors: NonEmptyChain[Error]): ujson.Value = {
    read[ujson.Value](write(errors.toChain.toList))
  }

  final def getRootCause(throwable: Throwable): String = {
    val rootCause: String = {
      @scala.annotation.tailrec
      def inner(t: Throwable, v: Vector[Throwable]): Vector[Throwable] = {
        val tail = v :+ t
        if (t.getCause == null) tail else inner(t.getCause, tail)
      }
      val causes: Vector[Throwable] = inner(throwable, Vector[Throwable]())
      causes.map(getStackTrace(_)).mkString("\n")
    }
    if (isEmpty(rootCause)) {
      "None"
    } else {
      rootCause
    }
  }
}
