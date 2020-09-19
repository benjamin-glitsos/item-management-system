import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel
import scala.util.matching.Regex

object UserValidators extends ValidationUtilities with MathUtilities {
    private val usernameLengthBounds = 4 to 16
    private val maxCharUsageProportion = 1d/4

    def isUserNotFound(u: List[User], username: String): Validation[User] = {
        val code = "USERNAME_NOT_FOUND"
        val message = s"No user with the username '${username}' was found."
        if (u.isEmpty) Error(code, message).invalidNel else u.head.validNel
    }

    private def isUsernameValidLength(username: String): Validation[String] = {
        if (!isWithinRange(username.length, usernameLengthBounds)) {
            val aboveOrBelowTheLength = if (username.length < usernameLengthBounds.min) "below the minimum" else "above the maximum"
            Error("USERNAME_NOT_VALID_LENGTH", s"The username provided is ${aboveOrBelowTheLength} of ${usernameLengthBounds.max} characters in length. The provided username is ${username.size} characters in length.").invalidNel
        } else {
            username.validNel
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

    def isPasswordValid(password: String): Validation[String] = {
        val validators = List(doesPasswordContainNumber(_), doesPasswordContainLowercaseLetter(_), doesPasswordContainCapitalLetter(_))
        multipleValidations[String](password, validators)
    }





    // def isPasswordValid(password: String): ValidatedNel[Error, String] = {
    //     def hasOverusedChars(username: String): ValidatedNel[Error, String] = {
    //         val length: Int = username.size
    //         val charUsageAsProportions: List[Long] = countAll[String](username)
    //             .values
    //             .map(_ / length)
    //         val charsExceeding: Int = charUsageAsProportions.count(_ > maxCharUsageProportion)
    //
    //         if(charsExceeding > 0) {
    //             val areSingleOrMultipleChars = if (charsExceeding > 1) s"is ${charsExceeding} characters" else s"are ${charsExceeding} characters"
    //             Error(code(9), s"There ${areSingleOrMultipleChars} in this password that are overused. A character cannot represent more than ${maxCharUsageProportion.toString} of the entire password.")
    //         } else {
    //             username.validNel
    //         }
    //     }
    // }
}
