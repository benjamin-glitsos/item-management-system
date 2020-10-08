ALTER TABLE records
ADD CONSTRAINT fk_object
FOREIGN KEY (object_id)
REFERENCES objects (id);

ALTER TABLE records
ADD CONSTRAINT fk_created_by
FOREIGN KEY (created_by_id)
REFERENCES users (id);

ALTER TABLE records
ADD CONSTRAINT fk_opened_by
FOREIGN KEY (opened_by_id)
REFERENCES users (id);

ALTER TABLE records
ADD CONSTRAINT fk_edited_by
FOREIGN KEY (edited_by_id)
REFERENCES users (id);

ALTER TABLE records
ADD CONSTRAINT fk_deleted_by
FOREIGN KEY (deleted_by_id)
REFERENCES users (id);

ALTER TABLE records
ADD CONSTRAINT fk_restored_by
FOREIGN KEY (restored_by_id)
REFERENCES users (id);

ALTER TABLE users
ADD CONSTRAINT fk_records
FOREIGN KEY (record_id)
REFERENCES records (id)
ON DELETE CASCADE;

ALTER TABLE stock
ADD CONSTRAINT fk_records
FOREIGN KEY (record_id)
REFERENCES records (id)
ON DELETE CASCADE;

ALTER TABLE transactions
ADD CONSTRAINT fk_records
FOREIGN KEY (record_id)
REFERENCES records (id)
ON DELETE CASCADE;

ALTER TABLE transactions
ADD CONSTRAINT fk_actions
FOREIGN KEY (action_id)
REFERENCES actions (id);
