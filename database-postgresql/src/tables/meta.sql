CREATE TABLE meta (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
  , metakey METAKEY UNIQUE NOT NULL DEFAULT generate_random_metakey()
  , opens POSITIVE_INTEGER NOT NULL DEFAULT 0
  , edits POSITIVE_INTEGER NOT NULL DEFAULT 0
  , is_deleted BOOLEAN NOT NULL DEFAULT false
  , created_at TIMESTAMPTZ NOT NULL DEFAULT now()
  , edited_at TIMESTAMPTZ
  , deleted_at TIMESTAMPTZ
  , created_by INTEGER NOT NULL
  , edited_by INTEGER
  , deleted_by INTEGER
  , additional_notes LONG_TEXT
);
