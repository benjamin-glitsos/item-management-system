trait LogicMixin {
  final def all(propositions: List[Boolean]): Boolean =
    propositions.forall(identity)
}
