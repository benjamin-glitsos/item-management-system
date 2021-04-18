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
    val trimmedSearch = trimSearch(search)

    val matchesKeyFragment: Option[Fragment] =
      trimmedSearch.map(s => fr"key ILIKE ${s"%$s%"}")

    val matchesNameFragment: Option[Fragment] =
      trimmedSearch.map(s => fr"name ILIKE ${s"%$s%"}")

    val matchesDescriptionFragment: Option[Fragment] =
      search.map(s => fr"description ILIKE ${s"%$s%"}")

    val whereFragment: Fragment =
      whereOrOpt(
        matchesKeyFragment,
        matchesNameFragment,
        matchesDescriptionFragment
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
            String,
            Option[String]
        )
      ]
      .to[List]
  }
}
