import scala.math.{round, pow}
import scala.util.Random
import org.apache.commons.lang3.StringUtils.{left, right}

trait SeederTrait {
  implicit final def times(n: Int) = new {
    def times(fn: => Unit) = {
      val seedFactor: Double = System.getenv("CONTROLLER_SEED_FACTOR").toDouble
      val count: Int         = round(n * seedFactor).toInt
      for (i <- 1 to count) fn
    }
  }

  private final def powerOfTen(p: Int): Long = {
    pow(10, p).toLong
  }

  final def biasedCoinFlip(probability: Double): Boolean = {
    val precision = powerOfTen(2).toInt
    val flip      = Random.between(1, precision).toDouble / precision
    probability >= flip
  }

  final def generatePassword(length: Int): String = {
    Seq.fill(length)(Random.nextPrintableChar()).mkString(new String)
  }

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

  final def createKey(words: String): String = {
    def abbreviate(word: String): String = if (word.length() <= 3) {
      word
    } else {
      val firstChars = left(word, 2)
      val lastChar   = right(word, 1)
      firstChars + lastChar
    }

    val randomCode =
      Random.alphanumeric
        .take(randomGaussianDiscrete(min = 1, max = 5))
        .mkString

    (words.split(" ").take(4).map(abbreviate) :+ randomCode)
      .filter(!StringUtilities.isEmpty(_))
      .mkString("-")
      .toUpperCase()
  }

  final def toTitleCase(s: String): String =
    s.split(" ").map(_.capitalize).mkString(" ")

  protected val count: Int = 0
  protected def clearData(): Unit = {}
  protected def predefinedData(): Unit = {}
  protected def seed(): Unit = {}
  protected def apply(): Unit = {}
}
