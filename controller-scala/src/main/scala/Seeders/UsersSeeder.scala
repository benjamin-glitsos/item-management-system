import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer

object UsersSeeder extends SeederMixin with MarkdownSeederMixin {
  override final val count: Int = 15

  override final def reset(): Unit =
    UsersService.delete(method = "hard-delete-all-rows")

  override final def defaults(): Unit = {
    val fairy: Fairy       = Fairy.create()
    val text: TextProducer = fairy.textProducer()

    UsersService.create(
      username = System.getenv("DEMO_USER_USERNAME"),
      emailAddress = System.getenv("DEMO_USER_EMAIL_ADDRESS"),
      firstName = System.getenv("DEMO_USER_FIRST_NAME"),
      lastName = System.getenv("DEMO_USER_LAST_NAME"),
      otherNames = sys.env.get("DEMO_USER_OTHER_NAMES"),
      password = System.getenv("DEMO_USER_PASSWORD"),
      additionalNotes = generateMarkdown(text)
    )
  }

  override final def seed(): Unit = {
    val fairy: Fairy             = Fairy.create()
    val person: Person           = fairy.person()
    val text: TextProducer       = fairy.textProducer()
    val randomNumberCode: String = randomBetween(1, 99).toString

    val username: String                = person.getUsername() + randomNumberCode
    val emailAddress: String            = makeEmailAddress(person, randomNumberCode)
    val firstName: String               = person.getFirstName()
    val lastName: String                = person.getLastName()
    val otherNames: Option[String]      = makeOtherNames(fairy)
    val password: String                = randomPrintable(length = 15)
    val additionalNotes: Option[String] = generateMarkdown(text)

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

  override final def apply(): Unit = {
    reset()
    defaults()
    count times seed()
  }
}
