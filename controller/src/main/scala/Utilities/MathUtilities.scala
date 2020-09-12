trait MathUtilities {
    def isWithinRange(n: Int, r: Range): Boolean = {
        n >= r.min && n <= r.max
    }

    def countAll[A](ls: List[A]): Map[A, Int] = {
        ls.groupBy(identity).mapValues(_.size)
    }
}
