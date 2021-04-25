ALTER TABLE meta
ADD CONSTRAINT meta_created_by_fk
FOREIGN KEY (created_by) 
REFERENCES users (id);

ALTER TABLE meta
ADD CONSTRAINT meta_edited_by_fk
FOREIGN KEY (edited_by) 
REFERENCES users (id);

ALTER TABLE meta
ADD CONSTRAINT meta_deleted_by_fk
FOREIGN KEY (deleted_by) 
REFERENCES users (id);
