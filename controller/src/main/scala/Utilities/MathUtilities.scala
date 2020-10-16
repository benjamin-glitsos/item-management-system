trait MathUtilities {
    def countAll[A](ls: List[A]): Map[A, Int] = {
        ls.groupBy(identity).mapValues(_.size).toMap
    }
}
