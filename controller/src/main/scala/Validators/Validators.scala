import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

object Validators extends ValidationUtilities {
    def getRequiredField(key: String, fields: Fields): Validation[String] = {
        val code = "REQUIRED_FIELD_NOT_PROVIDED"
        val message = s"The required field '$key' was not provided."
        val field = Some(key)
        fields.get(key) match {
            case Some(x) => x.validNel
            case None => Error(code, message, field).invalidNel
        }
    }
}
