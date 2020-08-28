import java.sql.Date
import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import scala.math.pow

trait SeederUtilities {
    private def powerOfTen(p: Int): Int = {
        pow(10, p).toInt
    }

    def newPerson(): Person = {
        Fairy.create().person()
    }

    def newText(): TextProducer = {
        Fairy.create().textProducer()
    }

    def normaliseEmptyString(s: String): Option[String] = {
        if (s.isEmpty) None else Some(s)
    }

    def randomFixedDigits(n: Int): Int = {
        val digits = powerOfTen(n)
        digits + Random.nextInt(digits * 9)
    }

    def randomSentences(min: Int, max: Int): String = {
        newText().latinSentence(Random.between(min, max + 1))
    }

    def currentDate(): Date = {
        new Date(System.currentTimeMillis())
    }

    def randomBinary(): Int = {
        Random.nextInt(2)
    }

    def coinFlip(): Boolean = {
        randomBinary > 0
    }

    def biasedFlip(probability: Double): Boolean = {
        val precision = powerOfTen(2)
        Random.nextInt(precision + 1) > precision * (1 - probability)
    }

    def randomExists[A](probability: Double, x: A): Option[A] = {
        if (biasedFlip(probability)) Some(x) else None
    }

    def randomNotes(): Option[String] = {
        randomExists(1/10, randomSentences(1, 4))
    }
}
