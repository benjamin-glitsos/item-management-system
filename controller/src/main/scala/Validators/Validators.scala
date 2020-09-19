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
        println(body)
        body.hcursor.get[String](key).toOption match {
            case Some(value) => value.validNel
            case None => Error(code, message, field).invalidNel
        }
    }
}
