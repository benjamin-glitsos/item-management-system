import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import OptionMixin.emptyStringToOption

object UsersSeeder extends EntitySeederMixin {
  override final val count: Int = 15

  final def clearData(): Unit = {
    UsersService.delete(method = "hard-delete-all-rows")
  }

  final def predefinedData(): Unit = {
    val fairy: Fairy       = Fairy.create();
    val text: TextProducer = fairy.textProducer();

    UsersService.create(
      username = System.getenv("DEMO_USER_USERNAME"),
      emailAddress = System.getenv("DEMO_USER_EMAIL_ADDRESS"),
      firstName = System.getenv("DEMO_USER_FIRST_NAME"),
      lastName = System.getenv("DEMO_USER_LAST_NAME"),
      otherNames = sys.env.get("DEMO_USER_OTHER_NAMES"),
      password = System.getenv("DEMO_USER_PASSWORD"),
      additionalNotes = MarkdownSeeder(text)
    )
  }

  final def seed(): Unit = {
    def seedRow(): Unit = {
      val fairy: Fairy       = Fairy.create();
      val person: Person     = fairy.person();
      val text: TextProducer = fairy.textProducer();

      val randomNumber: String = randomBetween(1, 99).toString
      val username: String =
        person.getUsername() + randomNumber
      val emailAddress: String = {
        val atSymbol: String = "@"
        val Array(emailUserName, emailDomainName) =
          person.getEmail().split(atSymbol)
        emailUserName + randomNumber + atSymbol + emailDomainName
      }

      val firstName: String = person.getFirstName()
      val lastName: String  = person.getLastName()
      val otherNames: Option[String] = emptyStringToOption(
        repeatedRunArray[String](
          randomGaussianDiscrete(min = 0, max = 2),
          () => {
            val person: Person = fairy.person()
            person.getMiddleName
          }
        ).mkString(" ").trim()
      )
      val password: String                = randomPrintable(length = 15)
      val additionalNotes: Option[String] = MarkdownSeeder(text)

      UsersService.create(
        username,
        emailAddress,
        firstName,
        lastName,
        otherNames,
        password,
        additionalNotes
      )
    }

    count times seedRow()
  }

  final def apply(): Unit = {
    clearData()
    predefinedData()
    seed()
  }
}
