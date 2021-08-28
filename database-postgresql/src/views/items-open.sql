CREATE VIEW items_open AS
    SELECT
        i.sku
      , i.upc
      , i.name
      , i.description
      , i.acquisition_date
      , i.expiration_date
      , i.unit_cost
      , i.unit_price
      , i.quantity_available
      , i.quantity_sold
      , m.additional_notes
      , m.metakey
      , m.opens
      , m.edits
      , m.is_deleted
      , EXTRACT(epoch FROM m.created_at) AS created_at
      , m.created_by
      , EXTRACT(epoch FROM m.edited_at) AS edited_at
      , m.edited_by
      , EXTRACT(epoch FROM m.deleted_at) AS deleted_at
      , m.deleted_by
    FROM items i
    INNER JOIN meta_open m ON i.meta_id = m.id;
