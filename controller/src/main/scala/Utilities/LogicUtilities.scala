trait LogicUtilities {
    def any(ls: List[Boolean]) = {
        ls.exists(x => x)
    }

    def all(ls: List[Boolean]) = {
        ls.forall(x => x)
    }
}
