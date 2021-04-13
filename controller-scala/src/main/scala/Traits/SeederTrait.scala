import scala.math.round
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

  final def randomIntegerBetween(min: Int, max: Int): Int =
    Random.between(min, max)

  final def biasedCoinFlip(probability: Double): Boolean = {
    val precision = MathUtilities.powerOfTen(2).toInt
    val flip      = Random.between(1, precision).toDouble / precision
    probability >= flip
  }

  final def randomPrintableChars(length: Int): String = {
    Seq.fill(length)(Random.nextPrintableChar()).mkString(new String)
  }

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

  final def createKey(words: String): String = {
    def abbreviate(word: String): String = if (word.length() <= 3) {
      word
    } else {
      val firstChars = left(word, 2)
      val lastChar   = right(word, 1)
      firstChars + lastChar
    }

    val randomCode = randomAlphanumerics(
      randomGaussianDiscrete(min = 1, max = 6)
    )

    (words.split(" ").take(4).map(abbreviate) :+ randomCode)
      .filter(!StringUtilities.isEmpty(_))
      .mkString("-")
      .toUpperCase()
  }
}
