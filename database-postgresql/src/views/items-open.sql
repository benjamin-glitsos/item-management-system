CREATE VIEW items_open AS
    SELECT
        i.key
      , i.name
      , i.description
      , m.metakey
      , m.opens
      , m.edits
      , m.is_deleted
      , m.created_at
      , m.edited_at
      , m.deleted_at
      , m.additional_notes
    FROM items i
    INNER JOIN meta m ON i.meta_id = m.id;