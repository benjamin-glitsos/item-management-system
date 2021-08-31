trait StringMixin {
  final def isEmpty(s: String): Boolean = s == null || s.isEmpty

  final def toTitleCase(s: String): String =
    s.split(" ").map(_.capitalize).mkString(" ")

  final def formatName(
      firstName: String,
      lastName: String,
      otherNames: Option[String]
  ): String = {
    otherNames match {
      case None => List(firstName, lastName).mkString(" ")
      case Some(otherNames) => {
        val otherNamesInitials =
          otherNames.split(" ").filter(_.length >= 1).map(_.charAt(0) + ".")
        (List(firstName) ++ otherNamesInitials ++ List(lastName))
          .mkString(" ")
      }

    }
  }
}
