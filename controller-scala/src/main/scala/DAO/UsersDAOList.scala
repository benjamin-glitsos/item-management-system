import doobie.Fragment
import doobie.Fragments.{whereAndOpt}
import doobie._
import doobie.implicits._
import doobie.implicits.javatime._

trait UsersDAOList {
  final def list(offset: Int, pageLength: Int, search: Option[String]) = {
    val select: Fragment = fr"SELECT *"

    val from: Fragment = fr"FROM users_list"

    val matchesSearch = search.map(s => fr"username ILIKE ${s"%$s%"}")

    // val matchesSearch: Option[Fragment] =
    //   search.map(s => fr"""
    //     | (username LIKE '%$s%'
    //     | OR email_address LIKE '%$s%'
    //     | OR first_name LIKE '$s')
    //     """)

    // ++ sort ++ page

    val where: Fragment = whereAndOpt(matchesSearch)

    val sort: Fragment = fr"ORDER BY edited_at DESC"

    val page: Fragment = fr"LIMIT $pageLength OFFSET $offset"

    (select ++ from ++ where).query[UsersList].to[List]
  }
}
