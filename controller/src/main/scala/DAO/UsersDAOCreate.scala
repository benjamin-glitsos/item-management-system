import doobie_bundle.database.dc._

trait UsersDAOCreate {
  def create(
      username: String,
      password: String,
      emailAddress: String,
      notes: String
  ) = {
    run(
      quote(
        query[UsersWithMeta]
          .insert(
            _.username      -> lift(username),
            _.password      -> lift(password),
            _.email_address -> lift(emailAddress),
            _.notes         -> lift(notes)
          )
      )
    )
  }
}
