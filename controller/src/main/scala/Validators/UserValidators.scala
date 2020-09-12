import cats.data.ValidatedNel
import cats.syntax.validated._

object UserValidators {
    def userExists(u: List[User]): ValidatedNel[Error, User] = {
        if (u.isEmpty) UserErrors.userDoesntExist.invalidNel else u.head.validNel
    }

    // def usernameIsValid(username: String): Error.Validated[String] = {
    //     if (username.length >= 4) {
    //         username.validNec
    //     } else {
    //         "Username must be at least four characters in length.".invalidNec
    //     }
    // }
}
