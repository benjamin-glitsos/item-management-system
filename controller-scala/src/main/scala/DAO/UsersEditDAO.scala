import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait UsersEditDAO extends DoobieDatabaseMixin {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      firstName: Option[String],
      lastName: Option[String],
      otherNames: Option[Option[String]],
      emailAddress: Option[String],
      password: Option[String],
      additionalNotes: Option[Option[String]]
  ) = {
    val update: Fragment =
      fr"UPDATE users_open"

    val set: Fragment = setOpt(
      newUsername.map(s => fr"username=$s"),
      firstName.map(s => fr"first_name=$s"),
      lastName.map(s => fr"last_name=$s"),
      otherNames.map(s => fr"other_names=$s"),
      emailAddress.map(s => fr"email_address=$s"),
      password.map(s => fr"password=$s"),
      additionalNotes.map(s => fr"additional_notes=$s")
    )

    val where: Fragment = whereAnd(fr"username=$oldUsername")

    val returning: Fragment = fr"""
    RETURNING username
            , email_address
            , first_name
            , last_name
            , other_names
            , password
            , additional_notes
            , metakey
            , opens
            , edits
            , is_deleted
            , created_at
            , created_by
            , edited_at
            , edited_by
            , deleted_at
            , deleted_by
    """

    (update ++ set ++ where ++ returning).query[UsersOpen].to[List]
  }
}
