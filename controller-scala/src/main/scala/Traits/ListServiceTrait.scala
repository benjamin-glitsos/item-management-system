import scala.util.Try
import Math.ceil

trait ListServiceTrait {
  final def calculatePageCount(pageLength: Int, items: Int): Int = {
    ceil(items.toFloat / pageLength).toInt
  }

  final def getOrZero(n: Int): Int = Try(n).getOrElse(0)
}
