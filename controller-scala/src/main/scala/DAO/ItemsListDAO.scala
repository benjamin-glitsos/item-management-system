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

    val sortKeyFragment: Fragment = Fragment.const(sort._1)

    val sortOrderFragment: Fragment = Fragment.const(sort._2)

    val sortFragment: Fragment =
      fr"ORDER BY" ++ sortKeyFragment ++ sortOrderFragment

    val pageFragment: Fragment = fr"LIMIT $pageLength OFFSET $offset"

    val queryFragment: Fragment = fr"""
    WITH total AS(
        SELECT
            *
          , COUNT(*) OVER() AS total_count
        FROM items_list
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
