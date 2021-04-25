CREATE VIEW meta_list AS
    SELECT
      , id
      , created_at
      , edited_at
    FROM meta
    WHERE is_deleted IS false;
