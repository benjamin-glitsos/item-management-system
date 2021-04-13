CREATE VIEW items_list AS
    SELECT
        key
      , name
      , abbreviate_multiline_text(description) AS description
      , created_at
      , edited_at
    FROM items_with_meta
    WHERE is_deleted IS false;
