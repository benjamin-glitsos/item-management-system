trait UsersCreateDAO extends DoobieDatabaseMixin {
  import dc._

  final def create(
      username: String,
      emailAddress: String,
      firstName: String,
      lastName: String,
      otherNames: Option[String],
      password: String,
      additionalNotes: Option[String]
  ) = run(
    quote(
      query[UsersOpen]
        .insert(
          _.username         -> lift(username),
          _.email_address    -> lift(emailAddress),
          _.first_name       -> lift(firstName),
          _.last_name        -> lift(lastName),
          _.other_names      -> lift(otherNames),
          _.password         -> lift(password),
          _.additional_notes -> lift(additionalNotes)
        )
    )
  )
}
