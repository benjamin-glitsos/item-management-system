CREATE TABLE meta (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
  , metakey METAKEY UNIQUE NOT NULL DEFAULT gen_metakey()
  , opens INT_PVE NOT NULL DEFAULT 0
  , edits INT_PVE NOT NULL DEFAULT 0
  , is_deleted BOOLEAN NOT NULL DEFAULT false
  , created_at TIMESTAMPTZ NOT NULL DEFAULT now()
  , edited_at TIMESTAMPTZ
  , deleted_at TIMESTAMPTZ
  , created_by INTEGER NOT NULL
  , edited_by INTEGER
  , deleted_by INTEGER
  , additional_notes LONG_TEXT
);
