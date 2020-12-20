import doobie._

object UsersServices {
  def list(
      offset: Int,
      pageLength: Int
  ): ConnectionIO[List[UsersList]] = UsersDAO.list(offset, pageLength)
}
