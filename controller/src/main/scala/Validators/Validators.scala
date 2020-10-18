import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

import io.circe.{Error => _, _}
import io.circe.syntax._

object Validators extends ValidationUtilities with LogicUtilities with TextUtilities {
    def validatePageRange[A](rangeStart: Int, rangeEnd: Int, count: Int, data: A): Validation[A] = {
        val code = "page_out_of_range"
        val message = s"The page of results that you have requested (items $rangeStart - $rangeEnd) is out of range of the total count of results ($count)."
        val field = None
        if (all(List(rangeStart < count, rangeStart >= 1, count >= 0))) {
            data.validNel
        } else {
            Error(code, message, field).invalidNel
        }
    }
}
