#!/bin/bash

STAFF_DEPARTMENTS=${STAFF_TABLE}_${DEPARTMENTS_TABLE}

psql << EOF

----- Create custom functionality -----

CREATE EXTENSION citext;
CREATE DOMAIN email AS citext
    CHECK ( value ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_\`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$' );

----- Create tables -----

CREATE TABLE $SEX_TABLE (
    id serial PRIMARY KEY
  , name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE $RECORDS_TABLE (
    id SERIAL PRIMARY KEY
  , uuid UUID UNIQUE NOT NULL
  , created_at TIMESTAMP DEFAULT NOW()
  , created_by SMALLINT NOT NULL
  , edits SMALLINT DEFAULT 0
  , edited_at TIMESTAMP
  , edited_by SMALLINT
  , deleted_at TIMESTAMP
  , deleted_by SMALLINT
);

CREATE TABLE $ROLES_TABLE (
    id serial PRIMARY KEY
  , name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE $DEPARTMENTS_TABLE (
    id serial PRIMARY KEY
  , name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE $PEOPLE_TABLE (
    id SERIAL PRIMARY KEY
  , first_name VARCHAR(255) NOT NULL
  , last_name VARCHAR(255) NOT NULL
  , other_names VARCHAR(255)
  , sex_id SMALLINT NOT NULL
  , date_of_birth DATE NOT NULL
  , email_address EMAIL NOT NULL
  , phone_number VARCHAR(20) NOT NULL
  , address_line_one VARCHAR(255) NOT NULL
  , address_line_two VARCHAR(255)
  , postcode VARCHAR(20) NOT NULL
  , aboriginal_or_torres_strait_islander BOOLEAN NOT NULL
  , australian_citizen BOOLEAN NOT NULL
  , born_overseas BOOLEAN NOT NULL
  , english_second_language BOOLEAN NOT NULL
);

CREATE TABLE $STAFF_TABLE (
    id serial PRIMARY KEY
  , record_id SMALLINT NOT NULL
  , person_id SMALLINT NOT NULL
  , staff_number VARCHAR(12) NOT NULL
  , employment_start DATE NOT NULL
  , employment_end DATE
);

CREATE TABLE $PATIENTS_TABLE (
    id serial PRIMARY KEY
  , record_id SMALLINT NOT NULL
  , person_id SMALLINT NOT NULL
  , patient_number VARCHAR(12) NOT NULL
  , medicare_number VARCHAR(10)
  , medicare_ref INT
  , medicare_expiry DATE
  , UNIQUE(medicare_number, medicare_ref)
);

CREATE TABLE $STAFF_DEPARTMENTS (
    staff_id SMALLINT NOT NULL
  , department_id SMALLINT NOT NULL
  , PRIMARY KEY(staff_id, department_id)
);

CREATE TABLE $VISITS_TABLE (
    id serial PRIMARY KEY
  , patient_id SMALLINT NOT NULL
  , staff_id SMALLINT NOT NULL
  , start_date DATE NOT NULL
  , end_date DATE
  , UNIQUE(patient_id, staff_id, start_date)
);

CREATE TABLE $USERS_TABLE (
    id SERIAL PRIMARY KEY
  , record_id SMALLINT NOT NULL
  , staff_id SMALLINT UNIQUE NOT NULL
  , username VARCHAR(20) UNIQUE NOT NULL
  , password VARCHAR(20) NOT NULL
);

----- Populate with starting values -----

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

----- Create relationships -----

ALTER TABLE $RECORDS_TABLE
ADD CONSTRAINT fk_created_by
FOREIGN KEY (created_by)
REFERENCES $USERS_TABLE (id);

ALTER TABLE $RECORDS_TABLE
ADD CONSTRAINT fk_edited_by
FOREIGN KEY (edited_by)
REFERENCES $USERS_TABLE (id);

ALTER TABLE $RECORDS_TABLE
ADD CONSTRAINT fk_deleted_by
FOREIGN KEY (deleted_by)
REFERENCES $USERS_TABLE (id);

ALTER TABLE $STAFF_TABLE
ADD CONSTRAINT fk_record_id
FOREIGN KEY (record_id)
REFERENCES $RECORDS_TABLE (id);

ALTER TABLE $STAFF_TABLE
ADD CONSTRAINT fk_person_id
FOREIGN KEY (person_id)
REFERENCES $PEOPLE_TABLE (id);

ALTER TABLE $PATIENTS_TABLE
ADD CONSTRAINT fk_record_id
FOREIGN KEY (record_id)
REFERENCES $RECORDS_TABLE (id);

ALTER TABLE $PATIENTS_TABLE
ADD CONSTRAINT fk_person_id
FOREIGN KEY (person_id)
REFERENCES $PEOPLE_TABLE (id);

ALTER TABLE $STAFF_DEPARTMENTS
ADD CONSTRAINT fk_staff_id
FOREIGN KEY (staff_id)
REFERENCES $STAFF_TABLE (id);

ALTER TABLE $STAFF_DEPARTMENTS
ADD CONSTRAINT fk_department_id
FOREIGN KEY (department_id)
REFERENCES $DEPARTMENTS_TABLE (id);

ALTER TABLE $VISITS_TABLE
ADD CONSTRAINT fk_patient_id
FOREIGN KEY (patient_id)
REFERENCES $PATIENTS_TABLE (id);

ALTER TABLE $VISITS_TABLE
ADD CONSTRAINT fk_staff_id
FOREIGN KEY (staff_id)
REFERENCES $STAFF_TABLE (id);

ALTER TABLE $USERS_TABLE
ADD CONSTRAINT fk_${RECORDS_TABLE}
FOREIGN KEY (record_id)
REFERENCES $RECORDS_TABLE (id);

ALTER TABLE $USERS_TABLE
ADD CONSTRAINT fk_${STAFF_TABLE}
FOREIGN KEY (staff_id)
REFERENCES $STAFF_TABLE (id);

EOF
