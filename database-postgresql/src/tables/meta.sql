CREATE TABLE meta (
    id SERIAL PRIMARY KEY
  , metakey METAKEY UNIQUE NOT NULL DEFAULT generate_random_metakey('')
  , opens INTEGER NOT NULL DEFAULT 0
  , edits INTEGER NOT NULL DEFAULT 0
  , is_deleted BOOLEAN NOT NULL DEFAULT no
  , created_at TIMESTAMP NOT NULL DEFAULT NOW()
  , edited_at TIMESTAMP
  , deleted_at TIMESTAMP
  , notes TEXT
);
