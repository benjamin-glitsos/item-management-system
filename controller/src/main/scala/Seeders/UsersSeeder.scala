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

// WITH meta_insert_1 AS (SELECT insert_default_meta() AS id)
//    , meta_insert_2 AS (SELECT insert_default_meta() AS id)
// INSERT INTO users (meta_id, email_address, username, password) VALUES (
//     (SELECT id FROM meta_insert_1)
//   , '$SUPER_ADMIN_EMAIL_ADDRESS'
//   , '$SUPER_ADMIN_USERNAME'
//   , '$SUPER_ADMIN_PASSWORD'
// ),(
//     (SELECT id FROM meta_insert_2)
//   , '$DEMO_ADMIN_EMAIL_ADDRESS'
//   , '$DEMO_ADMIN_USERNAME'
//   , '$DEMO_ADMIN_PASSWORD'
// );
