import cats.Applicative
import cats.implicits._
import cats.data.ValidatedNel
// import cats.syntax.validated._

object UserValidators extends ValidationUtilities with MathUtilities {
    private val usernameLengthBounds = 4 to 16
    private val maxCharUsageProportion = 1d/4

    def isUserNotFound(u: List[User], username: String): Validation[User] = {
        val code = "USERNAME_NOT_FOUND"
        val message = s"No user with the username '${username}' was found."
        if (u.isEmpty) Error(code, message).invalidNel else u.head.validNel
    }

    final case class RegistrationData(username: String, password: String, firstName: String, lastName: String, age: Int)
    private def validateUserName(userName: String): Validation[String] =
      if (userName.matches("^[a-zA-Z0-9]+$")) userName.validNel else Error("wow", "ok").invalidNel

    private def validatePassword(password: String): Validation[String] =
      if (password.matches("(?=^.{10,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")) password.validNel
      else Error("wow", "ok").invalidNel

    private def validateFirstName(firstName: String): Validation[String] =
      if (firstName.matches("^[a-zA-Z]+$")) firstName.validNel else Error("wow", "ok").invalidNel

    private def validateLastName(lastName: String): Validation[String] =
      if (lastName.matches("^[a-zA-Z]+$")) lastName.validNel else Error("wow", "ok").invalidNel

    private def validateAge(age: Int): Validation[Int] =
      if (age >= 18 && age <= 75) age.validNel else Error("wow", "ok").invalidNel

    def validateForm(username: String, password: String, firstName: String, lastName: String, age: Int): Validation[RegistrationData] = {
        (validateUserName(username),
        validatePassword(password),
        validateFirstName(firstName),
        validateLastName(lastName),
        validateAge(age)).mapN(RegistrationData)
    }




    // import cats.Semigroup
    //
    // def parallelValidate[E : Semigroup, A, B, C](v1: Validated[E, A], v2: Validated[E, B])(f: (A, B) => C): Validated[E, C] =
    //     (v1, v2) match {
    //         case (Valid(a), Valid(b))       => Valid(f(a, b))
    //         case (Valid(_), i@Invalid(_))   => i
    //         case (i@Invalid(_), Valid(_))   => i
    //         case (Invalid(e1), Invalid(e2)) => Invalid(Semigroup[E].combine(e1, e2))
    //     }




    // def isUsernameValid(username: String): ValidatedNel[Error, String] = {
    //     def isValidLength(username: String): ValidatedNel[Error, String] = {
    //         if (!isWithinRange(username.length, usernameLengthBounds)) {
    //             val aboveOrBelowTheLength = if (username.length < usernameLengthBounds.min) "below the minimum" else "above the maximum"
    //             Error(code(2), s"The username provided is ${aboveOrBelowTheLength} of ${validUsernameLength.max} characters in length. The provided username (${username}) is ${username.size} characters in length.")
    //         } else {
    //             username.validNel
    //         }
    //     }
    //
    //     def hasAtLeastOneSymbol(username: String): ValidatedNel[Error, String] = {
    //     }
    //
    //     isUsernameValidLength(username) |+| hasAtLeastOneSymbol(username) map { _ + _ }
    // }

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
