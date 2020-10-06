INSERT INTO sex (name) VALUES ('Male'), ('Female');

INSERT INTO roles (name)
VALUES
    ('System Admin')
  , ('Office Admin')
  , ('Office User')
  , ('Doctor')
  , ('Nurse');

INSERT INTO departments (name)
VALUES
    ('Anaesthetics and Pain Management')
  , ('Cardiology')
  , ('Dermatology')
  , ('Drug Health Service')
  , ('Emergency')
  , ('Endocrinology')
  , ('Gastroenterology')
  , ('Haematology')
  , ('Immunology')
  , ('Intensive Care Unit')
  , ('Microbiology and Infectious Diseases')
  , ('National Centre for Veterans’ Healthcare (NCVH)')
  , ('NSW Institute of Sports Medicine')
  , ('Neurosurgery')
  , ('Ophthalmology')
  , ('Orthopaedics')
  , ('Plastic, Reconstructive and Hand Surgery Unit')
  , ('Podiatry')
  , ('Pre-Admission Clinic')
  , ('Psychology')
  , ('Radiology')
  , ('Speech Pathology')
  , ('Vascular');

INSERT INTO people (
    first_name
  , last_name
  , other_names
  , sex_id
  , date_of_birth
  , email_address
  , phone_number
  , address_line_one
  , address_line_two
  , postcode
  , is_aboriginal_or_torres_strait_islander
  , is_australian_citizen
  , is_born_overseas
  , is_english_second_language
) VALUES (
    '$SUPER_FIRST_NAME'
  , '$SUPER_LAST_NAME'
  , '$SUPER_MIDDLE_NAME'
  , '$SUPER_SEX'
  , '$SUPER_DATE_OF_BIRTH'
  , '$SUPER_EMAIL'
  , '$SUPER_PHONE'
  , '$SUPER_ADDRESS_LINE_1'
  , '$SUPER_ADDRESS_LINE_2'
  , '$SUPER_POSTCODE'
  , '$SUPER_ABORIGINAL_OR_TORRES_STRAIT_ISLANDER'
  , '$SUPER_AUSTRALIAN_CITIZEN'
  , '$SUPER_BORN_OVERSEAS'
  , '$SUPER_ENGLISH_SECOND_LANGUAGE'
);

INSERT INTO departments (
    staff_id
  , department_id
) VALUES (
    1
  , 1
);

WITH staff_records_insert AS (
    INSERT INTO records (
        uuid
      , created_by
      , notes
    ) VALUES (
        '$SUPER_STAFF_UUID'
      , 1
      , '$SUPER_ADMIN_RECORD_NOTE'
    )
    RETURNING id
)
INSERT INTO staff (
    record_id
  , person_id
  , staff_number
  , employment_start
) VALUES (
    (SELECT id FROM staff_records_insert)
  , 1
  , '$SUPER_STAFF_NUMBER'
  , '$SUPER_EMPLOYMENT_START'
);

WITH user_records_insert AS (
    INSERT INTO records (
        uuid
      , created_by
      , notes
    ) VALUES (
        '$SUPER_USER_UUID'
      , 1
      , '$SUPER_ADMIN_RECORD_NOTE'
    )
    RETURNING id
)
INSERT INTO users (
    record_id
  , staff_id
  , username
  , password
) VALUES (
    (SELECT id FROM user_records_insert)
  , 1
  , '$SUPER_USERNAME'
  , '$SUPER_PASSWORD'
);
