import bundles.doobie.database._
import bundles.doobie.database.dc._

object StaffDAO {
    val name = sys.env.getOrElse("STAFF_TABLE", "staff")

    def create(s: Staff) = {
        run(quote(
            query[Staff].insert(
                _.record_id -> lift(s.record_id),
                _.person_id -> lift(s.person_id),
                _.staff_number -> lift(s.staff_number),
                _.employment_start -> lift(s.employment_start),
                _.employment_end -> lift(s.employment_end)
            ).returningGenerated(_.id)
        ))
    }

    def summary(id: Int) = {
        run(quote(
            (for {
                s <- query[Staff].filter(_.id == lift(id))
                p <- query[Person].join(_.id == s.person_id)
            } yield (StaffSummary(
                first_name = p.first_name,
                other_names = p.other_names,
                last_name = p.last_name,
                staff_number = s.staff_number
            )))
        )).head
    }
}
