trait LoggingUtilities {
    def logSmallHeading(message: String) = {
        val decoration = "#" * 7
        println(s"${decoration} ${message} ${decoration}")
    }
}
