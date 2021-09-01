import java.util.Date
import scala.math.{round, BigDecimal}
import scala.util.Random
import org.apache.commons.lang3.StringUtils.{left, right}
import java.time.{Instant, Duration}
import com.devskiller.jfairy.Fairy
import com.devskiller.jfairy.producer.person.Person

trait SeederMixin extends StringMixin with MathMixin with OptionMixin {
  protected val count: Int = 0
  protected def reset(): Unit
  protected def defaults(): Unit
  protected def seed(): Unit
  protected def apply(): Unit

  implicit final def times(n: Int) = new {
    def times(fn: => Unit) = {
      val seedFactor: Double = System.getenv("CONTROLLER_SEED_FACTOR").toDouble
      val count: Int         = round(n * seedFactor).toInt
      for (i <- 1 to count) fn
    }
  }

  final def randomBetween(min: Int, max: Int): Int =
    Random.between(min, max)

  final def coinFlip(): Boolean = Random.nextBoolean

  final def biasedCoinFlip(probability: Double): Boolean = {
    val precision = powerOfTen(2).toInt
    val flip      = Random.between(1, precision).toDouble / precision
    probability >= flip
  }

  final def randomFixedLength(random: () => Char, length: Int): String = {
    Seq.fill(length)(random()).mkString(new String)
  }

  final def randomPrintable(length: Int): String = randomFixedLength(
    () => Random.nextPrintableChar,
    length
  )

  final def randomNumbers(length: Int): String = randomFixedLength(
    () => Random.nextInt(9).toString.charAt(0),
    length
  )

  final def randomAlphanumerics(length: Int): String = Random.alphanumeric
    .take(length)
    .mkString

  final def repeatedRunArray[A](n: Int, fn: () => A): List[A] = {
    (1 to n).map(_ => fn()).toList
  }

  final def randomGaussianDiscrete(
      min: Int,
      max: Int,
      mean: Double = 1,
      sd: Double = 1
  ): Int = {
    def sample(i: Int = 1): Int = {
      if (i <= 3) {
        val normalSample: Double = Random.nextGaussian() * sd + mean

        val halfNormalSample: Double = if (normalSample <= mean) {
          normalSample
        } else {
          val d: Double = normalSample - mean
          normalSample + d
        }

        val discreteHalfNormalSample: Int = round(halfNormalSample).toInt

        if (min to max contains discreteHalfNormalSample) {
          discreteHalfNormalSample
        } else {
          sample(i + 1)
        }
      } else {
        min
      }
    }

    sample()
  }

  final def overwriteText(c: Char, s: String): String = s.map(x => c)

  final def randomUpc(): String = randomNumbers(12)

  final def randomSku(words: String): String = {
    def abbreviate(word: String): String = if (word.length() <= 3) {
      word
    } else {
      val firstChars: String = left(word, 2)
      val lastChar: String   = right(word, 1)
      firstChars + lastChar
    }

    val randomCode: String = randomAlphanumerics(
      randomGaussianDiscrete(min = 1, max = 6)
    )

    val blacklist: List[String] = List("pee", "pus")

    def censorBlacklist(code: String): String = {
      if (blacklist contains code) {
        overwriteText(randomAlphanumerics(1)(0), code)
      } else {
        code
      }
    }

    (words
      .split(" ")
      .take(4)
      .map(abbreviate)
      .map(_.toLowerCase())
      .map(censorBlacklist) :+ randomCode)
      .filter(!isEmpty(_))
      .mkString("-")
      .toUpperCase()
  }

  final def randomDouble = Random.nextDouble

  final def randomCurrency(): Double = {
    val d: Double = randomDouble * 10
    val b: BigDecimal =
      BigDecimal.valueOf(d).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    b.doubleValue
  }

  final def randomDateBetween(min: Date, max: Date): Date = {
    Date.from(
      Instant.ofEpochMilli(Random.between(min.getTime, max.getTime + 1))
    )
  }

  final def yearsAgo(years: Int): Date = {
    val daysAgo: Int   = years * 365
    val today: Instant = Instant.now()
    val past: Instant  = today.minus(Duration.ofDays(daysAgo))
    Date.from(past)
  }

  final def addRandomDays(date: Date, min: Int, max: Int): Date = {
    val future: Instant =
      date.toInstant.plus(Duration.ofDays(randomBetween(min, max)))
    Date.from(future)
  }

  final def makeEmailAddress(person: Person, code: String) = {
    val atSymbol: String = "@"
    val Array(emailUserName, emailDomainName) =
      person.getEmail().split(atSymbol)
    emailUserName + code + atSymbol + emailDomainName
  }

  final def makeOtherNames(fairy: Fairy) = maybeEmpty(
    repeatedRunArray[String](
      randomGaussianDiscrete(min = 0, max = 2),
      () => {
        val person: Person = fairy.person()
        person.getMiddleName
      }
    ).mkString(" ").trim()
  )
}
