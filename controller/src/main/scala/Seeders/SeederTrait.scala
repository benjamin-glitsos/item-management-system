import scala.math.round

trait SeederTrait {
  implicit def times(n: Int) = new {
    def times(fn: => Unit) = {
      val seedFactor: Double = System.getenv("SEED_FACTOR").toDouble
      val count: Int         = round(n * seedFactor).toInt
      for (i <- 1 to count) fn
    }
  }

  val count: Int = 0
  def clearData(): Unit = {}
  def predefinedData(): Unit = {}
  def seed(): Unit = {}
  def apply(): Unit = {}
}
