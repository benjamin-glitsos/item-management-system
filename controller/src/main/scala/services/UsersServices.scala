object UsersServices {
    def upsert(
        record: RecordEdit,
        person: PersonEdit,
        user: UserEdit
    ): ConnectionIO[Unit] = {
        for {
          r <- RecordsDAO.upsert(record)
          _ <- if (r.updated_by.isEmpty) {
                  UsersDAO.insert(r.id, person, user)
              } else {
                  UsersDAO.update(r.id, person, user)
              }
        } yield ()
    }
}
