import scala.math.pow

object MathMixin {
  final def powerOfTen(p: Int): Long = {
    pow(10, p).toLong
  }
}
