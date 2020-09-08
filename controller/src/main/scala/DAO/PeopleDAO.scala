import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._
import io.getquill.{ idiom => _, _ }
import bundles.doobie.database._
import bundles.doobie.database.dc._

object PeopleDAO {
    val name = sys.env.getOrElse("PEOPLE_TABLE", "people")

    def create(p: Person) = {
        run(quote(
            query[Person].insert(
                _.first_name -> lift(p.first_name),
                _.last_name -> lift(p.last_name),
                _.other_names -> lift(p.other_names),
                _.sex_id -> lift(p.sex_id),
                _.date_of_birth -> lift(p.date_of_birth),
                _.email_address -> lift(p.email_address),
                _.phone_number -> lift(p.phone_number),
                _.address_line_one -> lift(p.address_line_one),
                _.address_line_two -> lift(p.address_line_two),
                _.postcode -> lift(p.postcode),
                _.is_aboriginal_or_torres_strait_islander -> lift(p.is_aboriginal_or_torres_strait_islander),
                _.is_australian_citizen -> lift(p.is_australian_citizen),
                _.is_born_overseas -> lift(p.is_born_overseas),
                _.is_english_second_language -> lift(p.is_english_second_language)
            ).returningGenerated(_.id)
        ))
    }
}
