import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

import io.circe.{Error => _, _}
import io.circe.syntax._

import java.sql.SQLException

object Validators extends ValidationUtilities with TextUtilities {
    def databaseError(error: SQLException): Validation[String] = {
        val code = "database_error"
        val message = error.getMessage
        val field = None
        Error(code, message, field).invalidNel
    }
}
