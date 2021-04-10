CREATE TABLE meta (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
  , metakey METAKEY UNIQUE NOT NULL DEFAULT generate_random_metakey()
  , opens INTEGER NOT NULL DEFAULT 0
  , edits INTEGER NOT NULL DEFAULT 0
  , is_deleted BOOLEAN NOT NULL DEFAULT false
  , created_at TIMESTAMP NOT NULL DEFAULT NOW()
  , edited_at TIMESTAMP
  , deleted_at TIMESTAMP
  , additional_notes TEXT
);
