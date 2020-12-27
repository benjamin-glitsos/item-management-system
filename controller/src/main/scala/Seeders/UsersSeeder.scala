import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import scala.math.round
import scala.util.Random
import upickle.default._

object UsersSeeder {
  implicit def times(n: Int) = new {
    def times(fn: => Unit) = {
      val seedFactor: Double = System.getenv("SEED_FACTOR").toDouble
      val count: Int         = round(n * seedFactor).toInt
      for (i <- 1 to count) fn
    }
  }

  private def count: Int = 15

  private def clearData(): Unit = {
    UsersServices.delete(
      write(
        ujson.Obj(
          "method"    -> "hard-delete-all-rows",
          "usernames" -> ujson.Arr()
        )
      )
    )
  }

  private def predefinedData(): Unit = {
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

  private def seed(): Unit = {
    def seedRow(): Unit = {
      val fairy: Fairy       = Fairy.create();
      val person: Person     = fairy.person();
      val text: TextProducer = fairy.textProducer();

      val username: String     = person.getUsername()
      val password: String     = person.getPassword()
      val emailAddress: String = person.getEmail()
      val notes: String        = text.latinSentence(Random.between(1, 3))

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

  def apply(): Unit = {
    clearData()
    predefinedData()
    seed()
  }
}
