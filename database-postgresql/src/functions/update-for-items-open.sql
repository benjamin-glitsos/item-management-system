CREATE FUNCTION update_for_items_open(
    old_sku text
  , new_sku text
  , new_upc text
  , new_name text
  , new_description text
  , new_acquisition_date date
  , new_expiration_date date
  , new_unit_price money
  , new_unit_quantity integer
  , new_additional_notes text
)
RETURNS void AS $$
BEGIN
    WITH items_update AS (
        UPDATE items
        SET sku              = new_sku
          , name             = new_name
          , description      = new_description
          , acquisition_date = new_acquisition_date
          , expiration_date  = new_expiration_date
          , unit_price       = new_unit_price
          , unit_quantity    = new_unit_quantity
        WHERE sku = old_sku
        RETURNING meta_id
    )
    UPDATE meta
    SET edited_at        = NOW()
      , edited_by        = 1
      , additional_notes = new_additional_notes
      , edits            = edits + 1
    WHERE id = (SELECT meta_id FROM items_update);
END;
$$ LANGUAGE plpgsql;
