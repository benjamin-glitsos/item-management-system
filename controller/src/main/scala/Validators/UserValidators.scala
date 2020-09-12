import cats.data.ValidatedNel
import cats.syntax.validated._

// object UserValidators {
//     def username(username: String): Error.Validated[String] = {
//         if (username.length >= 4) {
//             username.validNec
//         } else {
//             "Username must be at least four characters in length.".invalidNec
//         }
//     }
// }
