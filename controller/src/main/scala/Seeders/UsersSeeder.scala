import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import scala.util.Random
import upickle.default._

object UsersSeeder {
  def apply(): Unit = {
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
}
