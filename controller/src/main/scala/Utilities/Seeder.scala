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

    def randomDigits(n: Int): Int = {
        val ran = new Random()
        val digits = pow(10, n).toInt
        digits + ran.nextInt(digits * 9)
    }

    def randomNotes(): Option[String] = {
        Some(newText().sentence(Random.between(1, 3)))
    }

    def currentDate(): Date = {
        new Date(System.currentTimeMillis())
    }

    def coinFlip(): Boolean = {
        Random.between(0, 1) == 0
    }
}
