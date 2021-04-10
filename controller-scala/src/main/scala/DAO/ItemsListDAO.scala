import java.time.LocalDateTime
import doobie.Fragment
import doobie.Fragments.{whereOrOpt}
import doobie._
import doobie.implicits._
import doobie.implicits.javatime._

trait ItemsListDAO {
  final def list(
      offset: Int,
      pageLength: Int,
      search: Option[String],
      sort: (String, String)
  ) = {
    val matchesKeyFragment: Option[Fragment] =
      search.map(s => fr"key ILIKE ${s"%$s%"}")

    val matchesNameFragment: Option[Fragment] =
      search.map(s => fr"name ILIKE ${s"%$s%"}")

    val matchesDescriptionFragment: Option[Fragment] =
      search.map(s => fr"description ILIKE ${s"%$s%"}")

    val whereFragment: Fragment =
      whereOrOpt(
        matchesKeyFragment,
        matchesNameFragment,
        matchesDescriptionFragment
      )

    val withListFragment_ = withListFragment(
      offset,
      pageLength,
      search,
      sort,
      whereFragment
    )

    val queryFragment: Fragment = fr"""
    $withListFragment_
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
