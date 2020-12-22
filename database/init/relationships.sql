ALTER TABLE meta
ADD CONSTRAINT fk_created_by
FOREIGN KEY (created_by_id)
REFERENCES users (id);

ALTER TABLE meta
ADD CONSTRAINT fk_opened_by
FOREIGN KEY (opened_by_id)
REFERENCES users (id);

ALTER TABLE meta
ADD CONSTRAINT fk_edited_by
FOREIGN KEY (edited_by_id)
REFERENCES users (id);

ALTER TABLE meta
ADD CONSTRAINT fk_deleted_by
FOREIGN KEY (deleted_by_id)
REFERENCES users (id);

ALTER TABLE meta
ADD CONSTRAINT fk_restored_by
FOREIGN KEY (restored_by_id)
REFERENCES users (id);

ALTER TABLE users
ADD CONSTRAINT fk_meta
FOREIGN KEY (meta_id)
REFERENCES meta (id);

ALTER TABLE stock
ADD CONSTRAINT fk_meta
FOREIGN KEY (meta_id)
REFERENCES meta (id);
