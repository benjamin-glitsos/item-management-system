ALTER TABLE records
ADD CONSTRAINT fk_created_by
FOREIGN KEY (created_by)
REFERENCES users (id);

ALTER TABLE records
ADD CONSTRAINT fk_edited_by
FOREIGN KEY (edited_by)
REFERENCES users (id);

ALTER TABLE records
ADD CONSTRAINT fk_deleted_by
FOREIGN KEY (deleted_by)
REFERENCES users (id);

ALTER TABLE records
ADD CONSTRAINT fk_restored_by
FOREIGN KEY (restored_by)
REFERENCES users (id);





















ALTER TABLE people
ADD CONSTRAINT fk_sex
FOREIGN KEY (sex_id)
REFERENCES sex (id);

ALTER TABLE staff
ADD CONSTRAINT fk_record_id
FOREIGN KEY (record_id)
REFERENCES records (id)
ON DELETE CASCADE;

ALTER TABLE staff
ADD CONSTRAINT fk_person_id
FOREIGN KEY (person_id)
REFERENCES people (id);

ALTER TABLE patients
ADD CONSTRAINT fk_record_id
FOREIGN KEY (record_id)
REFERENCES records (id)
ON DELETE CASCADE;

ALTER TABLE patients
ADD CONSTRAINT fk_person_id
FOREIGN KEY (person_id)
REFERENCES people (id);

ALTER TABLE staff_departments
ADD CONSTRAINT fk_staff_id
FOREIGN KEY (staff_id)
REFERENCES staff (id);

ALTER TABLE staff_departments
ADD CONSTRAINT fk_department_id
FOREIGN KEY (department_id)
REFERENCES departments (id);

ALTER TABLE visits
ADD CONSTRAINT fk_patient_id
FOREIGN KEY (patient_id)
REFERENCES patients (id);

ALTER TABLE visits
ADD CONSTRAINT fk_staff_id
FOREIGN KEY (staff_id)
REFERENCES staff (id);

ALTER TABLE users
ADD CONSTRAINT fk_records
FOREIGN KEY (record_id)
REFERENCES records (id)
ON DELETE CASCADE;

ALTER TABLE users
ADD CONSTRAINT fk_staff
FOREIGN KEY (staff_id)
REFERENCES staff (id);
