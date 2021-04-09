object LogicUtilities {
  final def all(propositions: List[Boolean]): Boolean =
    propositions.forall(identity)
}
