import scala.math.{round, pow}
import scala.util.Random

trait SeederTrait {
  implicit final def times(n: Int) = new {
    def times(fn: => Unit) = {
      val seedFactor: Double = System.getenv("SEED_FACTOR").toDouble
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

  final def gaussianRandom(
      min: Int,
      max: Int,
      skew: Int,
      bias: Int
  ): Int = {
    val range: Int             = max - min
    val mid: Double            = min + range / 2
    val gaussianRandom: Double = Random.nextGaussian()
    val biasFactor: Double     = Math.exp(bias)

    round(
      mid + (range * (biasFactor / (biasFactor + Math.exp(
        -gaussianRandom / skew
      )) - 0.5))
    ).toInt
  }

  protected val count: Int = 0
  protected def clearData(): Unit = {}
  protected def predefinedData(): Unit = {}
  protected def seed(): Unit = {}
  protected def apply(): Unit = {}
}
