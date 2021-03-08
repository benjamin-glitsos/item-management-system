import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import scala.util.Properties.envOrElse

object UsersSeeder extends SeederTrait {
  override final val count: Int = 15

  override final def clearData(): Unit = {
    UsersServices.delete(method = "hard-delete-all-rows", usernames = List())
  }

  override final def predefinedData(): Unit = {
    val fairy: Fairy       = Fairy.create();
    val text: TextProducer = fairy.textProducer();

    UsersServices.create(
      username = System.getenv("SUPER_ADMIN_USERNAME"),
      emailAddress = System.getenv("SUPER_ADMIN_EMAIL_ADDRESS"),
      firstName = System.getenv("SUPER_ADMIN_FIRST_NAME"),
      lastName = System.getenv("SUPER_ADMIN_LAST_NAME"),
      otherNames = envOrElse("SUPER_ADMIN_OTHER_NAMES", new String),
      password = System.getenv("SUPER_ADMIN_PASSWORD"),
      notes = MarkdownIpsum(text)
    )

    UsersServices.create(
      username = System.getenv("DEMO_ADMIN_USERNAME"),
      emailAddress = System.getenv("DEMO_ADMIN_EMAIL_ADDRESS"),
      firstName = System.getenv("DEMO_ADMIN_FIRST_NAME"),
      lastName = System.getenv("DEMO_ADMIN_LAST_NAME"),
      otherNames = envOrElse("DEMO_ADMIN_OTHER_NAMES", new String),
      password = System.getenv("DEMO_ADMIN_PASSWORD"),
      notes = MarkdownIpsum(text)
    )
  }

  override final def seed(): Unit = {
    def seedRow(): Unit = {
      val fairy: Fairy       = Fairy.create();
      val person: Person     = fairy.person();
      val text: TextProducer = fairy.textProducer();

      val username: String     = person.getUsername()
      val emailAddress: String = person.getEmail()
      val firstName: String    = person.getFirstName()
      val lastName: String     = person.getLastName()
      val otherNames: String = repeatedRunArray[String](
        randomGaussianDiscrete(min = 0, max = 2),
        () => {
          val person: Person = fairy.person()
          person.getMiddleName
        }
      ).mkString(" ").trim()
      val password: String = generatePassword(length = 15)
      val notes: String    = MarkdownIpsum(text)

      UsersServices.create(
        username,
        emailAddress,
        firstName,
        lastName,
        otherNames,
        password,
        notes
      )
    }

    count times seedRow()
  }

  override final def apply(): Unit = {
    clearData()
    predefinedData()
    seed()
  }
}
