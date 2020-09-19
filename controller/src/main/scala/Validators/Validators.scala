import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

import io.circe.{Error => _, _}
import io.circe.syntax._

import java.sql.SQLException

object Validators extends ValidationUtilities with LoggingUtilities {
    def getRequiredField(key: String, body: Json): Validation[String] = {
        val code = "REQUIRED_FIELD_NOT_PROVIDED"
        val message = s"The required field '$key' was not provided."
        val field = Some(key)
        body.hcursor.get[String](key).toOption match {
            case Some(value) => value.validNel
            case None => Error(code, message, field).invalidNel
        }
    }

    def sqlException(error: SQLException): Validation[String] = {
        val code = "SQL_EXCEPTION"
        val message = s"""
        |An error was thrown by the database.
        |
        |${smallHeading("Message:")}
        |
        |${error.getMessage}
        |
        |${smallHeading("State:")}
        |
        |${error.getSQLState}
        """
        val field = None
        println(error)
        Error(code, message, field).invalidNel
    }
}
