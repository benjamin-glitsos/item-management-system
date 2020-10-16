import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

import io.circe.{Error => _, _}
import io.circe.syntax._

import java.sql.SQLException

object Validators extends ValidationUtilities with LogicUtilities with TextUtilities {
    def databaseError(error: SQLException): Validation[String] = {
        val code = "database_error"
        val message = error.getMessage
        val field = None
        Error(code, message, field).invalidNel
    }

    def validatePageRange[A](rangeStart: Int, rangeEnd: Int, count: Int, success: A): Validation[A] = {
        val code = "page_out_of_range"
        val message = s"The page of results that you have requested ($rangeStart - $rangeEnd) is out of range of the total count of results ($count)."
        val field = None
        if (all(offset < count, offset >= 0, count >= 0)) {
            success.validNel
        } else {
            Error(code, message, field).invalidNel
        }
    }
}
