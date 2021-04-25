CREATE VIEW items_list AS
    SELECT
        i.key
      , i.name
      , abbreviate_multiline_text(i.description) AS description
      , m.created_at
      , m.edited_at
    FROM items i
    INNER JOIN meta m ON i.meta_id = m.id
    WHERE is_deleted IS false;
