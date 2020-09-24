trait TextUtilities {
    private def textHeading(size: Int, text: String): String = {
        val decoration = "#" * size
        println(s"${decoration} ${text} ${decoration}")
    }

    def consoleHeading(text: String): String = textHeading(7, text)

    def naturalList(list: List[String]): String = {
        val length = list.length

        length match {
            case 0 => ""
            case 1 => list.head
            case n => {
                val split = list.splitAt(length - 1)
                val commaSeparated = split._1.mkString(", ")
                List(commaSeparated, split._2).mkString(" and ")
            }
        }
    }
}
