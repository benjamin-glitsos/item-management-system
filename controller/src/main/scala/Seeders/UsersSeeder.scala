import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer

object UsersSeeder extends SeederTrait {
  override val count: Int = 15

  override def clearData(): Unit = {
    UsersServices.delete(method = "hard-delete-all-rows", usernames = List())
  }

  override def predefinedData(): Unit = {
    val fairy: Fairy       = Fairy.create();
    val text: TextProducer = fairy.textProducer();

    UsersServices.create(
      username = System.getenv("SUPER_ADMIN_USERNAME"),
      password = System.getenv("SUPER_ADMIN_PASSWORD"),
      emailAddress = System.getenv("SUPER_ADMIN_EMAIL_ADDRESS"),
      notes = MarkdownIpsum(text)
    )

    UsersServices.create(
      username = System.getenv("DEMO_ADMIN_USERNAME"),
      password = System.getenv("DEMO_ADMIN_PASSWORD"),
      emailAddress = System.getenv("DEMO_ADMIN_EMAIL_ADDRESS"),
      notes = MarkdownIpsum(text)
    )
  }

  override def seed(): Unit = {
    def seedRow(): Unit = {
      val fairy: Fairy       = Fairy.create();
      val person: Person     = fairy.person();
      val text: TextProducer = fairy.textProducer();

      val username: String     = person.getUsername()
      val password: String     = generatePassword(length = 15)
      val emailAddress: String = person.getEmail()
      val notes: String        = MarkdownIpsum(text)

      UsersServices.create(username, password, emailAddress, notes)
    }

    count times seedRow()
  }

  override def apply(): Unit = {
    clearData()
    predefinedData()
    seed()
  }
}
