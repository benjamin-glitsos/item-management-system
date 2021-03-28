import java.time.LocalDateTime
import doobie.Fragment
import doobie.Fragments.{whereOrOpt}
import doobie._
import doobie.implicits._
import doobie.implicits.javatime._

trait UsersListDAO {
  final def list(offset: Int, pageLength: Int, search: Option[String]) = {
    val select: Fragment = fr"""
    SELECT
      COUNT(*) OVER() as total_count
    , COUNT(*) OVER() as filtered_total_count
    ,  *
    """

    val from: Fragment = fr"FROM users_list"

    val matchesUsername = search.map(s => fr"username ILIKE ${s"%$s%"}")
    val matchesEmailAddress =
      search.map(s => fr"email_address ILIKE ${s"%$s%"}")
    val matchesName = search.map(s => fr"first_name ILIKE ${s"%$s%"}")

    val where: Fragment =
      whereOrOpt(matchesUsername, matchesEmailAddress, matchesName)

    val sort: Fragment = fr"ORDER BY edited_at DESC"

    val page: Fragment = fr"LIMIT $pageLength OFFSET $offset"

    (select ++ from ++ where ++ sort ++ page)
      .query[
        (
            Int,
            Int,
            String,
            String,
            String,
            String,
            Option[String],
            LocalDateTime,
            Option[LocalDateTime]
        )
      ]
      .to[List]
  }
}
