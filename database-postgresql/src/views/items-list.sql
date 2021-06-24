CREATE VIEW items_list AS
    SELECT
        i.sku
      , i.name
      , abbreviate_multiline_text(i.description) AS description
      , i.acquisition_date
      , m.created_at
      , m.edited_at
    FROM items i
    INNER JOIN meta_list m ON i.meta_id = m.id;
