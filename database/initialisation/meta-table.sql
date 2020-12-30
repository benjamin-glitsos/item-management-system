CREATE TABLE meta (
    id SERIAL PRIMARY KEY
  , metakey METAKEY UNIQUE NOT null DEFAULT gen_random_metakey('')
  , opens INTEGER NOT null DEFAULT 0
  , edits INTEGER NOT null DEFAULT 0
  , is_deleted BOOLEAN NOT null DEFAULT false
  , created_at TIMESTAMP NOT null DEFAULT NOW()
  , edited_at TIMESTAMP
  , deleted_at TIMESTAMP
  , restored_at TIMESTAMP
  , notes TEXT NOT null DEFAULT ''
);
