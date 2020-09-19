import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel

object Validators extends ValidationUtilities {
    def getField(required: Boolean, key: String, fields: Fields): Validation[String] = {
        val maybeField = fields.get(key)
        if (!required) {
            maybeField.validNel
        } else {
            val code = "REQUIRED_FIELD_NOT_PROVIDED"
            val message = s"The required field '$key' was not provided."
            val field = Some(key)
            maybeField match {
                case Some(x) => x.validNel
                case None => Error(code, message, field).invalidNel
            }
        }
    }
}
