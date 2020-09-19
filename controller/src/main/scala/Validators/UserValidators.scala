import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel
import scala.util.matching.Regex

object UserValidators extends ValidationUtilities with MathUtilities {
    private val passwordLengthBounds = 4 to 16
    private val passwordMaxCharUsageProportion = 1d/4

    def isUserNotFound(u: List[User], username: String): Validation[User] = {
        val code = "USERNAME_NOT_FOUND"
        val message = s"No user with the username '${username}' was found."
        if (u.isEmpty) Error(code, message).invalidNel else u.head.validNel
    }

    private def isPasswordValidLength(password: String): Validation[String] = {
        if (!isWithinRange(password.length, passwordLengthBounds)) {
            val aboveOrBelowTheLength = if (password.length < passwordLengthBounds.min) "below the minimum" else "above the maximum"
            Error(
                "PASSWORD_NOT_VALID_LENGTH",
                s"The password provided is ${aboveOrBelowTheLength} of ${passwordLengthBounds.max} characters in length. The provided password is ${password.size} characters in length."
            ).invalidNel
        } else {
            password.validNel
        }
    }

    private def doesPasswordContainNumber(password: String): Validation[String] = {
        doesStringContainPattern(
            string = password,
            pattern = "[0-9]".r,
            error = Error("PASSWORD_DOESNT_CONTAIN_NUMBER", "The password provided doesn't contain a number.")
        )
    }

    private def doesPasswordContainLowercaseLetter(password: String): Validation[String] = {
        doesStringContainPattern(
            string = password,
            pattern = "[a-z]".r,
            error = Error("PASSWORD_DOESNT_CONTAIN_LOWERCASE_LETTER", "The password provided doesn't contain a lowercase letter.")
        )
    }

    private def doesPasswordContainCapitalLetter(password: String): Validation[String] = {
        doesStringContainPattern(
            string = password,
            pattern = "[A-Z]".r,
            error = Error("PASSWORD_DOESNT_CONTAIN_CAPITAL_LETTER", "The password provided doesn't contain a capital letter.")
        )
    }

    def doesPasswordContainOverusedChars(password: String): Validation[String] = {
        val length: Int = password.size
        val charUsageAsProportions: List[Double] = countAll[Char](password.toList)
            .values
            .toList
            .map(_.toDouble / length)
        val charsExceeding: Int = charUsageAsProportions.count(_ > passwordMaxCharUsageProportion)
        val hasOverusedChars = charsExceeding > 0

        if(hasOverusedChars) {
            val areSingleOrMultipleChars = if (charsExceeding > 1) s"is ${charsExceeding} characters" else s"are ${charsExceeding} characters"
            Error(
                "PASSWORD_CONTAINS_OVERUSED_CHARACTERS",
                s"There ${areSingleOrMultipleChars} in this password that are overused. A character cannot represent more than ${passwordMaxCharUsageProportion.toString} of the entire password."
            ).invalidNel
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
            doesPasswordContainOverusedChars(_)
        )
        multipleValidations[String](password, validators)
    }
}
