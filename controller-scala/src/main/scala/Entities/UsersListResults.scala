case class UsersListResults(
    totalCount: Int,
    filteredCount: Int,
    pageStart: Int,
    pageEnd: Int,
    username: String,
    firstName: String,
    lastName: String,
    otherNames: String,
    emailAddress: String,
    createdAt: Int,
    editedAt: Option[Int]
)
