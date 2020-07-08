import cats._
import cats.data._
import cats.implicits._

// def sum(xs: List[Int]): Int = xs.foldLeft(0) { _ + _ }
// println(sum(List(1, 2, 3, 4)))

object Hello {
    def main(args: Array[String]) = {
        println("Hello, " |+| "World!")
    }
}
