import cats.data.ValidatedNel
import cats.syntax.validated._

object UserValidators {
    private val usernameMinLength = 4
    private val usernameMaxLength = 16

    private def isUsernameTooShort(username: String): ValidatedNel[Error, User] = {
        if (username.length <= usernameMinLength) UserErrors.usernameTooShort(username, usernameMinLength).invalidNel else username.validNel
    }

    private def isUsernameTooLong(username: String): ValidatedNel[Error, User] = {
        if (username.length <= usernameMinLength) UserErrors.usernameTooShort(username, usernameMinLength).invalidNel else username.validNel
    }

    def doesUserExist(u: List[User], username: String): ValidatedNel[Error, User] = {
        if (u.isEmpty) UserErrors.userDoesntExist(username).invalidNel else u.head.validNel
    }

    def isUsernameValid(username: String): ValidatedNel[Error, String] = {
        isUsernameTooShort(username) |+| isUsernameTooLong(username) map { _ + _ }
    }
}
