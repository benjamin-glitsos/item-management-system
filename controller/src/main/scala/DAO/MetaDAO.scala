// import bundles.doobie.database._
// import bundles.doobie.database.dc._
// import doobie._
// import java.time.LocalDateTime
//
// object RecordsDAO {
//     val recordsOpenView = quote {
//       querySchema[RecordOpen]("records_open_view")
//     }
//
//     def create(user_id: Int, notes: Option[String]) = {
//         run(quote(
//             query[Record].insert(
//                 _.created_at -> lift(LocalDateTime.now()),
//                 _.created_by -> lift(user_id),
//                 _.notes -> lift(notes)
//             ).returningGenerated(r => RecordIdentity(r.id))
//         ))
//     }
//
//     def edit(id: Int, user_id: Int, notes: Option[String]) = {
//         run(quote(
//             query[Record]
//                 .filter(_.id == lift(id))
//                 .update(
//                     x => x.edits -> (x.edits + 1),
//                     _.edited_at -> Some(lift(LocalDateTime.now())),
//                     _.edited_by -> Some(lift(user_id)),
//                     _.notes -> lift(notes)
//                 ).returning(r => RecordIdentity(r.id, r.uuid))
//         ))
//     }
//
//     def openBasic(id: Int) = {
//         run(quote(
//             query[Record].filter(_.id == lift(id))
//         )).map(_.head)
//     }
//
//     def open(id: Int) = {
//         run(quote(
//             recordsOpenView.filter(_.id == lift(id))
//         )).map(_.head)
//
//         // (for {
//         //     r <- query[Record].filter(_.id == lift(id))
//         //     creator <- query[User].join(_.id == r.created_by)
//         //     opener <- query[User].leftJoin(x => r.opened_by.exists(_ == x.id))
//         //     editor <- query[User].leftJoin(x => r.edited_by.exists(_ == x.id))
//         //     deletor <- query[User].leftJoin(x => r.deleted_by.exists(_ == x.id))
//         //     restorer <- query[User].leftJoin(x => r.restored_by.exists(_ == x.id))
//         // } yield (
//         //     RecordOpen(
//         //         uuid = r.uuid,
//         //         created_at = r.created_at,
//         //         created_by = creator.username,
//         //         opens = r.opens,
//         //         opened_at = r.opened_at,
//         //         opened_by = opener.map(_.username),
//         //         edits = r.edits,
//         //         edited_at = r.edited_at,
//         //         edited_by = editor.map(_.username),
//         //         deleted_at = r.deleted_at,
//         //         deleted_by = deletor.map(_.username),
//         //         restored_at = r.restored_at,
//         //         restored_by = restorer.map(_.username),
//         //         notes = r.notes
//         //     )
//         // ))
//     }
//
//     def opened(id: Int, user_id: Int) = {
//         run(quote(
//             query[Record]
//                 .filter(_.id == lift(id))
//                 .update(
//                     x => x.opens -> (x.opens + 1),
//                     _.opened_at -> Some(lift(LocalDateTime.now())),
//                     _.opened_by -> Some(lift(user_id)))
//                 .returning(r => RecordIdentity(r.id, r.uuid)
//         )))
//     }
//
//     def delete(id: Int, user_id: Int) = {
//         run(quote(
//             query[Record]
//                 .filter(_.id == lift(id))
//                 .update(
//                     _.deleted_at -> Some(lift(LocalDateTime.now())),
//                     _.deleted_by -> Some(lift(user_id)))
//                 .returning(r => RecordIdentity(r.id, r.uuid))
//         ))
//     }
//
//     def restore(id: Int, user_id: Int) = {
//         run(quote(
//             query[Record]
//                 .filter(x => x.id == lift(id))
//                 .update(
//                     _.deleted_at -> None,
//                     _.deleted_by -> None,
//                     _.restored_at -> Some(lift(LocalDateTime.now())),
//                     _.restored_by -> Some(lift(user_id))
//                 )
//                 .returning(r => RecordIdentity(r.id, r.uuid))
//         ))
//     }
//
//     def permanentlyDelete(id: Int) = {
//         run(quote(
//             query[Record]
//                 .filter(_.id == lift(id))
//                 .delete
//         ))
//     }
// }