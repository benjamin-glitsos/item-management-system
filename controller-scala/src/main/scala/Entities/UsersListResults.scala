case class UsersListResults(
    totalCount: Int,
    filteredCount: Int,
    pageStart: Int,
    pageEnd: Int,
    username: String,
    emailAddress: String,
    firstName: String,
    lastName: String,
    otherNames: Option[String],
    createdAt: Int,
    editedAt: Option[Int]
)
