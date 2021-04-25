CREATE VIEW meta_with_users AS
    SELECT
        m.id
      , m.metakey
      , m.opens
      , m.edits
      , m.is_deleted
      , m.created_at
      , m.edited_at
      , m.deleted_at
      , m.created_by
      , m.edited_by
      , m.deleted_by
      , m.created_at
      , m.edited_at
      , m.deleted_at
      , m.additional_notes
    FROM meta m
