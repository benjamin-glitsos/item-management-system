import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import scala.util.Random
import upickle.default._

object UsersSeeder extends SeederTrait {
  override val count: Int = 15

  override def clearData(): Unit = {
    UsersServices.delete(
      write(
        ujson.Obj(
          "method"    -> "hard-delete-all-rows",
          "usernames" -> ujson.Arr()
        )
      )
    )
  }

  override def predefinedData(): Unit = {
    UsersServices.create(
      write(
        ujson.Obj(
          "username"      -> System.getenv("SUPER_ADMIN_USERNAME"),
          "password"      -> System.getenv("SUPER_ADMIN_PASSWORD"),
          "email_address" -> System.getenv("SUPER_ADMIN_EMAIL_ADDRESS")
        )
      )
    )
    UsersServices.create(
      write(
        ujson.Obj(
          "username"      -> System.getenv("DEMO_ADMIN_USERNAME"),
          "password"      -> System.getenv("DEMO_ADMIN_PASSWORD"),
          "email_address" -> System.getenv("DEMO_ADMIN_EMAIL_ADDRESS")
        )
      )
    )
  }

  override def seed(): Unit = {
    def seedRow(): Unit = {
      val fairy: Fairy       = Fairy.create();
      val person: Person     = fairy.person();
      val text: TextProducer = fairy.textProducer();

      val username: String     = person.getUsername()
      val password: String     = person.getPassword()
      val emailAddress: String = person.getEmail()
      val notes: String =
        if (biasedCoinFlip(0.75)) {
          text.latinSentence(Random.between(1, 5))
        } else {
          ""
        }

      val entityJson: String = write(
        ujson.Obj(
          "username"      -> username,
          "password"      -> password,
          "email_address" -> emailAddress,
          "notes"         -> notes
        )
      )

      UsersServices.create(entityJson)
    }

    count times seedRow()
  }

  override def apply(): Unit = {
    clearData()
    predefinedData()
    seed()
  }
}
