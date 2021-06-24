CREATE FUNCTION insert_for_items_open(
    _sku text
  , _upc text
  , _name text
  , _description text
  , _acquisition_date date
  , _expiration_date date
  , _unit_price money
  , _unit_quantity integer
  , _additional_notes text
)
RETURNS void AS $$
BEGIN
    WITH insert_meta AS (
        INSERT INTO meta (metakey, created_by, additional_notes)
        VALUES (generate_random_metakey('items'), 1, _additional_notes)
        RETURNING id
    )
    INSERT INTO items (
        sku
      , upc
      , name
      , description
      , acquisition_date
      , expiration_date
      , unit_price
      , unit_quantity
      , meta_id
    )
    VALUES (
        _sku
      , _upc
      , _name
      , _description
      , _acquisition_date
      , _expiration_date
      , _unit_price
      , _unit_quantity
      , (SELECT id FROM insert_meta)
    );
END;
$$ LANGUAGE plpgsql;
