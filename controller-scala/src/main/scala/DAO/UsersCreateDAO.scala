import doobie_bundle.database.dc._

trait UsersCreateDAO {
  final def create(
      username: String,
      emailAddress: String,
      firstName: String,
      lastName: String,
      otherNames: Option[String],
      password: String,
      notes: Option[String]
  ) = {
    run(
      quote(
        query[UsersWithMeta]
          .insert(
            _.username      -> lift(username),
            _.email_address -> lift(emailAddress),
            _.first_name    -> lift(firstName),
            _.last_name     -> lift(lastName),
            _.other_names   -> lift(otherNames),
            _.password      -> lift(password),
            _.notes         -> lift(notes)
          )
      )
    )
  }
}
