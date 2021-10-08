import doobie.Fragment
import doobie.Fragments.whereOrOpt
import doobie._
import doobie.implicits._

trait UsersListDAO extends ListDAOMixin {
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

    val matchesFirstNameLastNameFragment: Option[Fragment] =
      trimmedSearch.map(s =>
        fr"CONCAT_WS(' ', first_name, last_name) ILIKE ${s"%$s%"}"
      )

    val matchesFirstNameOtherNamesLastNameFragment: Option[Fragment] =
      trimmedSearch.map(s =>
        fr"CONCAT_WS(' ', first_name, other_names, last_name) ILIKE ${s"%$s%"}"
      )

    val whereFragment: Fragment =
      whereOrOpt(
        matchesUsernameFragment,
        matchesEmailAddressFragment,
        matchesFirstNameLastNameFragment,
        matchesFirstNameOtherNamesLastNameFragment
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

    queryFragment.query[UsersListResults].to[List]
  }
}
