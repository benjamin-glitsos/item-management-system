object StringUtilities {
  final def friendlyName(
      firstName: String,
      lastName: String,
      otherNames: List[String]
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
