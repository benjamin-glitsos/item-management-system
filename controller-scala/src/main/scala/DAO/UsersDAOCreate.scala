import doobie_bundle.database.dc._

trait UsersDAOCreate {
  final def create(
      username: String,
      emailAddress: String,
      firstName: String,
      lastName: String,
      otherNames: String,
      password: String,
      notes: String
  ) = {
    run(
      quote(
        query[UsersWithMeta]
          .insert(
            _.username      -> lift(username),
            _.email_address -> lift(emailAddress),
            _.first_name    -> lift(firstName),
            _.last_name     -> lift(lastName),
            _.other_names   -> lift(Some(otherNames): Option[String]),
            _.password      -> lift(password),
            _.notes         -> lift(notes)
          )
      )
    )
  }
}
