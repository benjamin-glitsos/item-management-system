import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel
import scala.util.matching.Regex

object UserValidators extends ValidationUtilities with MathUtilities {
    private val passwordLengthBounds = 4 to 16
    private val passwordMaxCharUsageProportion = 1d/4

    def isUserWithUsernameNotFound(u: List[User]): Validation[User] = {
        val code = "USERNAME_NOT_FOUND"
        val message = s"No user with this username was found."
        val field = Some("username")
        if (u.isEmpty) Error(code, message, field).invalidNel else u.head.validNel
    }

    private def isPasswordValidLength(password: String): Validation[String] = {
        if (!isWithinRange(password.length, passwordLengthBounds)) {
            val aboveOrBelowTheLength = if (password.length < passwordLengthBounds.min) "below the minimum" else "above the maximum"
            val code = "PASSWORD_NOT_VALID_LENGTH"
            val message = s"The password provided is ${aboveOrBelowTheLength} of ${passwordLengthBounds.max} characters in length. The provided password is ${password.size} characters in length."
            val field = Some("password")
            Error(code, message, field).invalidNel
        } else {
            password.validNel
        }
    }

    private def doesPasswordContainNumber(password: String): Validation[String] = {
        val code = "PASSWORD_DOESNT_CONTAIN_NUMBER"
        val message = "The password provided doesn't contain a number."
        val field = Some("password")
        doesStringContainPattern(
            string = password,
            pattern = "[0-9]".r,
            error = Error(code, message, field)
        )
    }

    private def doesPasswordContainLowercaseLetter(password: String): Validation[String] = {
        val code = "PASSWORD_DOESNT_CONTAIN_LOWERCASE_LETTER"
        val message = "The password provided doesn't contain a lowercase letter."
        val field = Some("password")
        doesStringContainPattern(
            string = password,
            pattern = "[a-z]".r,
            error = Error(code, message, field)
        )
    }

    private def doesPasswordContainCapitalLetter(password: String): Validation[String] = {
        val code = "PASSWORD_DOESNT_CONTAIN_CAPITAL_LETTER"
        val message = "The password provided doesn't contain a capital letter."
        val field = Some("password")
        doesStringContainPattern(
            string = password,
            pattern = "[A-Z]".r,
            error = Error(code, message, field)
        )
    }

    private def doesPasswordContainSymbol(password: String): Validation[String] = {
        val code = "PASSWORD_DOESNT_CONTAIN_SYMBOL"
        val message = "The password provided doesn't contain at least one symbol (a character which is not a standard letter or number)."
        val field = Some("password")
        doesStringContainPattern(
            string = password,
            pattern = "[^0-9a-zA-Z]".r,
            error = Error(code, message, field)
        )
    }

    private def doesPasswordContainOverusedChars(password: String): Validation[String] = {
        val length: Int = password.size
        val charUsageAsProportions: List[Double] = countAll[Char](password.toList)
            .values
            .toList
            .map(_.toDouble / length)
        val charsExceeding: Int = charUsageAsProportions.count(_ > passwordMaxCharUsageProportion)
        val hasOverusedChars = charsExceeding > 0

        if(hasOverusedChars) {
            val areSingleOrMultipleChars = if (charsExceeding > 1) s"is ${charsExceeding} characters" else s"are ${charsExceeding} characters"
            val code = "PASSWORD_CONTAINS_OVERUSED_CHARACTERS"
            val message = s"There ${areSingleOrMultipleChars} in this password that are overused. A character cannot represent more than ${passwordMaxCharUsageProportion.toString} of the entire password."
            val field = Some("password")
            Error(code, message, field).invalidNel
        } else {
            password.validNel
        }
    }

    def isPasswordValid(password: String): Validation[String] = {
        val validators = List(
            isPasswordValidLength(_),
            doesPasswordContainNumber(_),
            doesPasswordContainLowercaseLetter(_),
            doesPasswordContainCapitalLetter(_),
            doesPasswordContainSymbol(_),
            doesPasswordContainOverusedChars(_)
        )
        multipleValidations[String](password, validators)
    }
}
