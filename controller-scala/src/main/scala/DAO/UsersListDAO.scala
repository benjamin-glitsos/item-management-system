import doobie.Fragment
import doobie.Fragments.whereOrOpt
import doobie._
import doobie.implicits._

trait UsersListDAO extends ListDAOTrait {
  final def list(
      offset: Int,
      pageLength: Int,
      search: Option[String],
      sort: Sort
  ) = {
    val trimmedSearch = trimSearch(search)

    val matchesUsernameFragment: Option[Fragment] =
      trimmedSearch.map(s => fr"username ILIKE ${s"%$s%"}")

    val matchesEmailAddressFragment: Option[Fragment] =
      trimmedSearch.map(s => fr"email_address ILIKE ${s"%$s%"}")

    val matchesFirstNameFragment: Option[Fragment] =
      trimmedSearch.map(s => fr"first_name ILIKE ${s"%$s%"}")

    val matchesLastNameFragment: Option[Fragment] =
      trimmedSearch.map(s => fr"last_name ILIKE ${s"%$s%"}")

    val matchesOtherNamesFragment: Option[Fragment] =
      trimmedSearch.map(s => fr"other_names ILIKE ${s"%$s%"}")

    val whereFragment: Fragment =
      whereOrOpt(
        matchesUsernameFragment,
        matchesEmailAddressFragment,
        matchesFirstNameFragment,
        matchesLastNameFragment,
        matchesOtherNamesFragment
      )

    val withListFragment: Fragment = listFragment(
      "users",
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
      , username
      , email_address
      , first_name
      , last_name
      , other_names
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
