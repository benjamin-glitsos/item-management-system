object Seeder {
  implicit def times(n: Int) = new {
    def times(fn: => Unit) =
      for (i <- 1 to n) fn // TODO: add the seed factor to this
  }

  def apply(): Unit = {
    15 times UsersSeeder()
  }
}
