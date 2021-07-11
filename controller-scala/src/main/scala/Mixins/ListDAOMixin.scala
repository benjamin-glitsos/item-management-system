import doobie.Fragment
import doobie._
import doobie.implicits._

trait ListDAOMixin extends ListMixin {
  final def trimSearch(search: Option[String]): Option[String] =
    search.map(_.trim)

  final def listFragment(
      table: String,
      offset: Int,
      pageLength: Int,
      search: Option[String],
      sort: Sort,
      whereFragment: Fragment
  ): Fragment = {
    val viewFragment: Fragment = Fragment.const(s"${table}_list")

    val sortKeyFragment: Fragment = Fragment.const(sort._1)

    val sortOrderFragment: Fragment = Fragment.const(sort._2)

    val sortFragment: Fragment =
      fr"ORDER BY" ++ sortKeyFragment ++ sortOrderFragment

    val pageFragment: Fragment = fr"LIMIT $pageLength OFFSET $offset"

    fr"""
    WITH total AS(
        SELECT
            *
          , COUNT(*) OVER() AS total_count
        FROM $viewFragment
    ), filtered AS(
        SELECT
            *
          , COUNT(*) OVER() AS filtered_count
        FROM total
        $whereFragment
        $sortFragment
    ), limited AS(
      SELECT
          *
        , row_number() OVER() AS row_number
      FROM filtered
      $pageFragment
    ), page AS(
      SELECT
          *
        , MIN(row_number) OVER() AS page_start
        , MAX(row_number) OVER() AS page_end
      FROM limited
    )
    """
  }
}
