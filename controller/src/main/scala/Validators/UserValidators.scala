import cats.data.ValidatedNel
import cats.syntax.validated._

object UserValidators {
    def userExists(u: List[User], username: String): ValidatedNel[Error, User] = {
        if (u.isEmpty) UserErrors.userDoesntExist(username).invalidNel else u.head.validNel
    }

    def usernameIsValid(username: String): ValidatedNel[Error, User] = {
        // TODO: accumulate all errors in username 
        if (username.length >= 4) {
            username.validNel
        } else {
            "Username must be at least four characters in length.".invalidNel
        }
    }
}
