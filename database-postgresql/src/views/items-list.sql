CREATE VIEW items_list AS
    SELECT
        i.sku
      , i.name
      , abbreviate_multiline_text(i.description) AS description
      , i.acquisition_date
      , EXTRACT(epoch FROM m.created_at) AS created_at
      , EXTRACT(epoch FROM m.edited_at) AS edited_at
    FROM items i
    INNER JOIN meta_list m ON i.meta_id = m.id;
