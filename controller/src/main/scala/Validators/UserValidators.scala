import cats.data.ValidatedNel
import cats.syntax.validated._

object UserValidators extends ValidationUtilities with MathUtilities {
    private def code(n: Int) = generateCode(sys.env.getOrElse("USERS_TABLE", "users"), n)

    private val usernameLengthBounds = 4 to 16
    private val maxCharUsageProportion = 1d/4

    def isUserNotFound(u: List[User], username: String): ValidatedNel[Error, User] = {
        if (u.isEmpty) {
            Error(code(1), s"No user with the username '${username}' was found.")
        } else {
            u.head.validNel
        }
    }

    def isUsernameValid(username: String): ValidatedNel[Error, String] = {
        def isValidLength(username: String): ValidatedNel[Error, String] = {
            if (!isWithinRange(username.length, usernameLengthBounds)) {
                val aboveOrBelowTheLength = if (username.length < usernameLengthBounds.min) "below the minimum" else "above the maximum"
                Error(code(2), s"The username provided is ${aboveOrBelowTheLength} of ${validUsernameLength.max} characters in length. The provided username (${username}) is ${username.size} characters in length.")
            } else {
                username.validNel
            }
        }

        def hasOverusedChars(username: String): ValidatedNel[Error, String] = {
            val length: Int = username.size
            val charUsageAsProportions: List[Long] = countAll[String](username)
                .values
                .map(_ / length)
            val charsExceeding: Int = charUsageAsProportions.count(_ > maxCharUsageProportion)

            if(charsExceeding > 0) {
                val areSingleOrMultipleChars = if (charsExceeding > 1) s"is ${charsExceeding} characters" else s"are ${charsExceeding} characters"
                Error(code(3), s"There ${areSingleOrMultipleChars} in this password that are overused. A character cannot represent more than ${maxCharUsageProportion.toString} of the entire password.")
            } else {
                username.validNel
            }
        }

        isUsernameValidLength(username) |+| hasOverusedChars(username) map { _ + _ }
    }
}
