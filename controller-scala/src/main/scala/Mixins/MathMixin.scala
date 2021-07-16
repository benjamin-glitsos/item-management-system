import scala.math.pow

trait MathMixin {
  final def powerOfTen(p: Int): Long = {
    pow(10, p).toLong
  }
}
