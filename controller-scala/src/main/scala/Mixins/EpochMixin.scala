trait EpochMixin {
  final def epochNow(): Long = System.currentTimeMillis / 1000

  final def minutes(seconds: Int) = seconds * 60
}
