import doobie.Fragment
import doobie.Fragments.{whereOrOpt}
import doobie._
import doobie.implicits._

trait ItemsListDAO extends ListDAOMixin {
  final def list(
      offset: Int,
      pageLength: Int,
      search: Option[String],
      sort: Sort
  ) = {
    val trimmedSearch = trimSearch(search)

    val matchesSkuFragment: Option[Fragment] =
      trimmedSearch.map(s => fr"sku ILIKE ${s"%$s%"}")

    val matchesNameFragment: Option[Fragment] =
      trimmedSearch.map(s => fr"name ILIKE ${s"%$s%"}")

    val matchesDescriptionFragment: Option[Fragment] =
      search.map(s => fr"description ILIKE ${s"%$s%"}")

    val whereFragment: Fragment =
      whereOrOpt(
        matchesSkuFragment,
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
      , sku
      , name
      , description
      , acquisition_date 
      , created_at
      , edited_at
    FROM page
    """

    queryFragment.query[ItemsListResults].to[List]
  }
}
