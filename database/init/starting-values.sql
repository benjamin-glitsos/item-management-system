INSERT INTO $SEX_TABLE (name) VALUES ('Male'), ('Female');

INSERT INTO $ROLES_TABLE (name)
VALUES
    ('Admin')
  , ('Doctor')
  , ('Nurse');

INSERT INTO $DEPARTMENTS_TABLE (name)
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

INSERT INTO $PEOPLE_TABLE (
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
  , aboriginal_or_torres_strait_islander
  , australian_citizen
  , born_overseas
  , english_second_language
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

INSERT INTO $STAFF_DEPARTMENTS (
    staff_id
  , department_id
) VALUES (
    1
  , 1
);

INSERT INTO $RECORDS_TABLE (
    uuid
  , created_by
) VALUES (
    '$SUPER_UUID'
  , 1
);

INSERT INTO $STAFF_TABLE (
    record_id
  , person_id
  , staff_number
  , employment_start
) VALUES (
    1
  , 1
  , '$SUPER_STAFF_NUMBER'
  , '$SUPER_EMPLOYMENT_START'
);

INSERT INTO $USERS_TABLE (
    record_id
  , staff_id
  , username
  , password
) VALUES (
    1
  , 1
  , '$SUPER_USERNAME'
  , '$SUPER_PASSWORD'
);
