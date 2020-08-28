import java.sql.Date
import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import scala.math.pow

trait Seeder {
    def newPerson(): Person = {
        Fairy.create().person()
    }

    def newText(): TextProducer = {
        Fairy.create().textProducer()
    }

    def normaliseEmptyString(s: String): Option[String] = {
        if (s.isEmpty) None else Some(s)
    }

    def randomDigits(n: Int): Int = {
        val digits = pow(10, n).toInt
        digits + Random.nextInt(digits * 9)
    }

    def randomNotes(): Option[String] = {
        normaliseEmptyString(newText().latinSentence(Random.nextInt(3)))
    }

    def currentDate(): Date = {
        new Date(System.currentTimeMillis())
    }

    def coinFlip(): Boolean = {
        Random.nextInt(1) > 0
    }
}
