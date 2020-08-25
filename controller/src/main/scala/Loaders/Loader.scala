import java.sql.Timestamp
import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import scala.math.pow

object Loader {
    implicit def multipleTimes(i: Int) = new {
        def times(fn: => Unit) = (1 to i) foreach (x => fn)
    }

    def randomDigits(n: Int) = {
        val ran = new Random()
        val digits = pow(10, n)
        digits + ran.nextInt(digits * 9)
    }

    def newPerson(): Person = {
        Fairy.create().person()
    }

    def newText(): TextProducer = {
        Fairy.create().textProducer()
    }

    def newUser() = {
        val person = newPerson()
        val text = newText()

        UsersServices.create(
            u = User(
                id = 0,
                record_id = 0,
                staff_id = randomDigits(12).toString(),
                username = person.getUsername(),
                password = person.getPassword()
            ),
            user_id = 1,
            notes = Some(
                text.sentence(Random.between(1, 3))
            )
        )
    }

    def run() = {
        15 times newUser()
    }
}
