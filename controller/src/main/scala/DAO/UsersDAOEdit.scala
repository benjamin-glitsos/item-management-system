import doobie_bundle.database.dc._
import java.time.LocalDateTime

trait UsersDAOEdit {
  def edit(
      oldUsername: String,
      newUsername: String,
      password: String,
      emailAddress: String,
      notes: String
  ) = {
    run(
      quote(
        query[UsersWithMeta]
          .filter(_.username == lift(oldUsername))
          .update(
            _.username      -> lift(newUsername),
            _.password      -> lift(password),
            _.email_address -> lift(emailAddress),
            _.notes         -> lift(notes)
          )
      )
    )
  }
}
