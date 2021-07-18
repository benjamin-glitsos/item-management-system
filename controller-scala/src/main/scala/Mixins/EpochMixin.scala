trait EpochMixin {
  final def epochNow(): Long = System.currentTimeMillis / 1000
}
