import java.sql.Timestamp
import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import scala.math.pow

trait Seeder {
    def randomDigits(n: Int) = {
        val ran = new Random()
        val digits = pow(10, n).toInt
        digits + ran.nextInt(digits * 9)
    }

    def newPerson(): Person = {
        Fairy.create().person()
    }

    def newText(): TextProducer = {
        Fairy.create().textProducer()
    }
}
