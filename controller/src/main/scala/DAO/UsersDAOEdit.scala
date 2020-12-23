import doobie_bundle.database.dc._

trait UsersDAOEdit {
  def edit(
      oldUsername: String,
      newUsername: Option[String],
      password: Option[String],
      emailAddress: Option[String],
      notes: Option[String]
  ) = {
    run(
      quote(
        dynamicQuery[UsersWithMeta]
          .filter(_.username == oldUsername)
          .update(
            setOpt(_.username, Some("test-setOpt"))
          )
      )
    )
  }
}
// _.username,
// username,
// _.password,
// password,
// _.email_address,
// email_address,
// _.notes,
// notes
