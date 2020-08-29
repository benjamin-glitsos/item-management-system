import java.sql.Date
import scala.util.Random
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person
import com.devskiller.jfairy.producer.text.TextProducer
import scala.math.pow

trait SeederUtilities {
    private def powerOfTen(p: Int): Long = {
        pow(10, p).toLong
    }
    def randomBetween(min: Int, max: Int): Int = {
        Random.between(min, max)
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

    def randomFixedDigits(n: Int): Long = {
        val digits = powerOfTen(n - 1)
        Random.nextLong(digits * 9) + digits
    }

    def randomSentences(min: Int, max: Int): String = {
        newText().latinSentence(randomBetween(min, max + 1))
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
        val flip = Random.nextInt(precision.toInt + 1)
        val bias = (precision - 1/2) * probability
        val decider = precision / 2
        flip + bias > decider
    }

    def randomExists[A](probability: Double, x: A): Option[A] = {
        if (biasedFlip(probability)) Some(x) else None
    }

    def randomNotes(): Option[String] = {
        randomExists(1/8, randomSentences(1, 4))
    }

    def randomString(length: Int): String = {
        val randomChar = if (biasedFlip(2/3)) Random.nextString(1).head else Random.nextPrintableChar()
        Seq.fill(length)(randomChar).mkString("")
    }
}
