CREATE VIEW items_list AS
    SELECT
        key
      , name
      , CONCAT(LEFT(description, 50), IF(LENGTH(description) > 50, '...', ''))
      , created_at
      , edited_at
    FROM items_with_meta
    WHERE is_deleted = false;
