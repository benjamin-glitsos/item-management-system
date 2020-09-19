object StaffServices {
    def create(
        s: Staff,
        p: Person,
        d_ids: List[Int],
        user_username: String,
        notes: Option[String]
    ) = {
        for {
          r_id <- RecordsDAO.create(user_username, notes)
          p_id <- PeopleDAO.create(p)
          s_id <- StaffDAO.create(s.copy(
              record_id = r_id,
              person_id = p_id
          ))
          _ <- StaffDepartmentsDAO.create(
              staff_id = s_id,
              department_ids = d_ids
          )
        } yield ()
    }
}
