trait LoggingUtilities {
    def logSmallHeading(message: String) = {
        val decoration = "*" * 3
        println(s"${decoration} ${message} ${decoration}")
    }
}
