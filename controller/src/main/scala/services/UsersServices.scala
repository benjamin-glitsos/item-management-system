import java.util.UUID
import java.time.LocalDateTime
import io.getquill._

object UsersServices {
    lazy val ctx = new PostgresJdbcContext(SnakeCase, "quill")
    import ctx._

    implicit val encodeUUID = MappedEncoding[UUID, String](_.toString)

    implicit val recordsInsertMeta = insertMeta[Records](_.id)

    def createOrUpdate(
        uuid: UUID,
        user_id: Int,
        user: Users,
        person: People
    ) = {
        ctx.translate(
            quote {
                RecordsDAO.upsert(uuid, user_id)
            }
        )
    }
}




// .returning(_.id)
// .returningGenerated(r => {
//     query[People]
//         .insert(lift(person.copy(record_id = r.id)))
//         .onConflictUpdate(_.record_id)(
//             (t, e) => t.first_name -> e.first_name,
//             (t, e) => t.last_name -> e.last_name,
//             (t, e) => t.other_names -> e.other_names,
//             (t, e) => t.sex_id -> e.sex_id,
//             (t, e) => t.email_address -> e.email_address,
//             (t, e) => t.phone_number -> e.phone_number,
//             (t, e) => t.address_line_one -> e.address_line_one,
//             (t, e) => t.address_line_two -> e.address_line_two,
//             (t, e) => t.zip -> e.zip
//         )
// })
// RecordsDAO.upsert(uuid, user_id).returningGenerated(r => {
//     PeopleDAO.upsert(
//         person.copy(record_id = r.id)
//     ).returningGenerated(p => {
//         UsersDAO.upsert(
//             user.copy(
//                 uuid = uuid,
//                 person_id = p.id
//             )
//         )
//     })
// })
