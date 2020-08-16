object UsersDAO {
    def insert(record_id: Int, person: PersonEdit, user: UserEdit) = {
        sql"""
        WITH new_person AS (
            INSERT INTO people (
              record_id
            , first_name
            , last_name
            , other_names
            , sex_id
            , email_address
            , phone_number
            , address_line_one
            , address_line_two
            , zip
            ) VALUES (
                  ${record_id}
                , ${person.first_name}
                , ${person.last_name}
                , ${person.other_names}
                , ${person.sex_id}
                , ${person.email_address}
                , ${person.phone_number}
                , ${person.address_line_one}
                , ${person.address_line_two}
                , ${person.zip}
            ) RETURNING id
        )
        INSERT INTO users (person_id, username, password)
        VALUES (
            (SELECT id FROM new_person)
          , ${user.username}
          , ${user.password}
        )
        """.update.run
    }

    def update(record_id: Int, person: PersonEdit, user: UserEdit) = {
        sql"""
        WITH existing_person AS (
            UPDATE people SET
                first_name       = ${person.first_name}
              , last_name        = ${person.last_name}
              , other_names      = ${person.other_names}
              , sex_id           = ${person.sex_id}
              , email_address    = ${person.email_address}
              , phone_number     = ${person.phone_number}
              , address_line_one = ${person.address_line_one}
              , address_line_two = ${person.address_line_two}
              , zip              = ${person.zip}
            WHERE record_id      = ${record_id}
            RETURNING id
        )
        UPDATE users SET
            username = ${user.username}
          , password = ${user.password}
        WHERE person_id = (SELECT id FROM existing_person)
        """.update.run
    }
}
