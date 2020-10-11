import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

import io.circe.{Error => _, _}
import io.circe.syntax._

import java.sql.SQLException

object Validators extends ValidationUtilities with TextUtilities {
    def getRequiredField(key: String, body: Json): Validation[String] = {
        val code = "required_field_not_provided"
        val message = s"The required field '$key' was not provided."
        val field = Some(key)
        body.hcursor.get[String](key).toOption match {
            case Some(value) => value.validNel
            case None => Error(code, message, field).invalidNel
        }
    }

    def getOptionalField(key: String, body: Json): Validation[Option[String]] = {
        body.hcursor.get[String](key).toOption.validNel
    }

    // def getDefaultedField[A](key: String, default: A, body: Json): Validation[Option[String]] = {
    //     body.hcursor.get[String](key).toOption.validNel
    // }

    def sqlException(error: SQLException): Validation[String] = {
        val code = "sql_exception"
        val message = error.getMessage
        val field = None
        Error(code, message, field).invalidNel
    }

    def isDeleteActionSupported(action: String): Validation[String] = {
        val supportedDeleteActions = List("soft", "restore", "hard")

        supportedDeleteActions.find(_ == action) match {
            case Some(x) => x
            case None => {
                val code = "unsupported_delete_action"
                val message = s"The delete action that was provided ('$action') is unsupported. Only the actions ${naturalList(supportedDeleteActions)} are supported."
                val field = Some("action")
                Error(code, message, field).invalidNel
            }
        }
    }
}
