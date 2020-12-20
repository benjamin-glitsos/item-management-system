trait LogicUtilities {
    def any(ls: List[Boolean]): Boolean = {
        ls.exists(x => x)
    }

    def all(ls: List[Boolean]): Boolean = {
        ls.forall(x => x)
    }
}
