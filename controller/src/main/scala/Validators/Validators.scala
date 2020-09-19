import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

import io.circe.{Error => _, _}
import io.circe.syntax._

object Validators extends ValidationUtilities {
    def getRequiredField(key: String, body: Json): Validation[String] = {
        val code = "REQUIRED_FIELD_NOT_PROVIDED"
        val message = s"The required field '$key' was not provided."
        val field = Some(key)
        body.hcursor.get[String](key).toOption match {
            case Some(value) => value.validNel
            case None => Error(code, message, field).invalidNel
        }
    }

    def hasNoneBeenCreated(ids: Int): Validation[Int] = {
        if (ids > 0) {
            ids.validNel
        } else {
            val code = "CREATE_OPERATION_FAILED"
            val message = "This item could be created due to an error."
            val field = None
            Error(code, message, field).invalidNel
        }
    }
}
