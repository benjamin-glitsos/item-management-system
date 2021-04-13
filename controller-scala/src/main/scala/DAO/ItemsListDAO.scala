import java.time.LocalDateTime
import doobie.Fragment
import doobie.Fragments.{whereOrOpt}
import doobie._
import doobie.implicits._
import doobie.implicits.javatime._

trait ItemsListDAO extends ListDAOTrait {
  final def list(
      offset: Int,
      pageLength: Int,
      search: Option[String],
      sort: Sort
  ) = {
    val matchesKeyFragment: Option[Fragment] =
      search.map(s => fr"key ~* $s")

    val matchesNameFragment: Option[Fragment] =
      search.map(s => fr"name ~* $s")

    val whereFragment: Fragment =
      whereOrOpt(
        matchesKeyFragment,
        matchesNameFragment
      )

    val withListFragment: Fragment = listFragment(
      "items",
      offset,
      pageLength,
      search,
      sort,
      whereFragment
    )

    val queryFragment: Fragment = fr"""
    $withListFragment
    SELECT 
        total_count
      , filtered_count
      , page_start
      , page_end
      , key
      , name
      , description
      , created_at
      , edited_at
    FROM page
    """

    queryFragment
      .query[
        (
            Int,
            Int,
            Int,
            Int,
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
