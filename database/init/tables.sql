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
  , notes TEXT
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
  , record_id SMALLINT UNIQUE NOT NULL
  , person_id SMALLINT UNIQUE NOT NULL
  , staff_number VARCHAR(12) UNIQUE NOT NULL
  , employment_start DATE NOT NULL
  , employment_end DATE
);

CREATE TABLE $PATIENTS_TABLE (
    id serial PRIMARY KEY
  , record_id SMALLINT UNIQUE NOT NULL
  , person_id SMALLINT UNIQUE NOT NULL
  , patient_number VARCHAR(12) UNIQUE NOT NULL
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
  , diagnosis TEXT
  , UNIQUE(patient_id, staff_id, start_date)
);

CREATE TABLE $USERS_TABLE (
    id SERIAL PRIMARY KEY
  , record_id SMALLINT UNIQUE NOT NULL
  , staff_id SMALLINT UNIQUE NOT NULL
  , username VARCHAR(20) UNIQUE NOT NULL
  , password VARCHAR(20) NOT NULL
);
