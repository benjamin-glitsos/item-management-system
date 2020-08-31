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

    def randomBetween(r: Range): Int = {
        Random.between(r.min, r.max)
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

    def randomSentences(r: Range): String = {
        newText().latinSentence(randomBetween(r.min to r.max + 1))
    }

    def currentDate(): Date = {
        new Date(System.currentTimeMillis())
    }

    def randomBinary(): Int = {
        randomBetween(0 to 1)
    }

    def coinFlip(): Boolean = {
        randomBinary >= 0
    }

    def biasedFlip(probability: Double): Boolean = {
        val precision = powerOfTen(2).toInt
        val flip = randomBetween(1 to precision).toDouble / precision
        probability >= flip
    }

    def randomExists[A](probability: Double, x: A): Option[A] = {
        if (biasedFlip(probability)) Some(x) else None
    }

    def randomNotes(): Option[String] = {
        randomExists(1d/8, randomSentences(1 to 4))
    }

    def randomString(length: Int): String = {
        def randomChar() = {
            if (biasedFlip(2d/3)) Random.alphanumeric(1) else Random.nextPrintableChar()
        }
        Seq.fill(length)(randomChar()).mkString("")
    }
}
