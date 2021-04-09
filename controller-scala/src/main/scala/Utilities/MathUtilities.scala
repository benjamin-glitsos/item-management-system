import scala.math.pow

object MathUtilities {
  final def powerOfTen(p: Int): Long = {
    pow(10, p).toLong
  }
}
