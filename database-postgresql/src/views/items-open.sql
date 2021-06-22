CREATE VIEW items_open AS
    SELECT
        i.sku
      , i.name
      , i.description
      , m.additional_notes
      , m.metakey
      , m.opens
      , m.edits
      , m.is_deleted
      , m.created_at
      , m.created_by
      , m.edited_at
      , m.edited_by
      , m.deleted_at
      , m.deleted_by
    FROM items i
    INNER JOIN meta_open m ON i.meta_id = m.id;
