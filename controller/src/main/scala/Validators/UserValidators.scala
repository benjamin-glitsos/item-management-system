import cats.data.ValidatedNel
import cats.syntax.validated._

object UserValidators {
    def userExists(u: List[User]) = {
        if (u.nonEmpty) {
            u.head.validNec
        } else {
            UserErrors.userDoesntExist.invalidNec
        }
    }

    // def usernameIsValid(username: String): Error.Validated[String] = {
    //     if (username.length >= 4) {
    //         username.validNec
    //     } else {
    //         "Username must be at least four characters in length.".invalidNec
    //     }
    // }
}
