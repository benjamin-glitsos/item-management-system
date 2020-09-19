trait LoggingUtilities {
    private def textHeading(size: Int, text: String) = {
        val decoration = "#" * size
        println(s"${decoration} ${text} ${decoration}")
    }

    def consoleHeading(text: String) = textHeading(7, text)

    def smallHeading(text: String) = textHeading(5, text)
}
