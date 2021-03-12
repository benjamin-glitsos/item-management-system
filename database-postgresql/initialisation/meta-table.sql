CREATE TABLE meta (
    id SERIAL PRIMARY KEY
  , metakey METAKEY UNIQUE NOT NULL DEFAULT gen_random_metakey('')
  , opens INTEGER NOT NULL DEFAULT 0
  , edits INTEGER NOT NULL DEFAULT 0
  , is_deleted BOOLEAN NOT NULL DEFAULT false
  , created_at TIMESTAMP NOT NULL DEFAULT NOW()
  , edited_at TIMESTAMP
  , deleted_at TIMESTAMP
  , restored_at TIMESTAMP
  , notes TEXT
);
