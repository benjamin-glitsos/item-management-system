CREATE VIEW meta_list AS
    SELECT
        id
      , EXTRACT(epoch FROM created_at) AS created_at
      , EXTRACT(epoch FROM edited_at) AS edited_at
    FROM meta
    WHERE is_deleted IS false;
