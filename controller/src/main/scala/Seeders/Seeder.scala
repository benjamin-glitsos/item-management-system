import scala.math.round

object Seeder {
  implicit def times(n: Int) = new {
    def times(fn: => Unit) = {
      val seedFactor: Double = System.getenv("SEED_FACTOR").toDouble
      val count: Int         = round(n * seedFactor).toInt
      for (i <- 1 to count) fn
    }
  }

  def apply() = {
    15 times UsersSeeder()
  }
}
