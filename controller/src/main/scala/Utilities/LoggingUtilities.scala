trait LoggingUtilities {
    private def textHeading(size: Int, text: String) = {
        val decoration = "#" * 7
        println(s"${decoration} ${message} ${decoration}")
    }

    def consoleHeading(text: String) = textHeading(7, text)

    def smallHeading(text: String) = textHeading(3, text)
}
