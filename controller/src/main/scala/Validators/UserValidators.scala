import cats.data.ValidatedNel
import cats.syntax.validated._

object UserValidators extends ValidationUtilities {
    private def code(n: Int) = generateCode(sys.env.getOrElse("USERS_TABLE", "users"), n)

    private val usernameMinLength = 4
    private val usernameMaxLength = 16

    private def usernameLengthDescription(username: String) = s"The provided username (${username}) is ${username.length} characters in length."

    def isUserNotFound(u: List[User], username: String): ValidatedNel[Error, User] = {
        if (u.isEmpty) {
            Error(code(1), s"No user with the username '${username}' was found.")
        } else {
            u.head.validNel
        }
    }

    def isUsernameValid(username: String): ValidatedNel[Error, String] = {
        def isUsernameTooShort(username: String): ValidatedNel[Error, User] = {
            if (username.length <= usernameMinLength) {
                Error(code(2), s"The username provided is underneath the minimum of ${min} characters in length. ${usernameLengthDescription(username)}")
            } else {
                username.validNel
            }
        }

        def isUsernameTooLong(username: String): ValidatedNel[Error, User] = {
            if (username.length <= usernameMinLength) {
                Error(code(3), s"The username provided is above the maximum of ${max} characters in length. ${usernameLengthDescription(username)}")
            } else {
                username.validNel
            }
        }

        isUsernameTooShort(username) |+| isUsernameTooLong(username) map { _ + _ }
    }
}
