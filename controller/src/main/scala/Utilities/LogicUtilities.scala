object LogicUtilities {
    def any(ls: List[Boolean]) = {
        ls.exists(x)
    }

    def all(ls: List[Boolean]) = {
        ls.forall(x)
    }
}
