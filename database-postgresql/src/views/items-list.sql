CREATE VIEW items_list AS
    SELECT
        key
      , name
      , truncate(description, 30)
      , created_at
      , edited_at
    FROM items_with_meta
    WHERE is_deleted = false;
