CREATE FUNCTION insert_for_items_open(
    _sku text
  , _upc text
  , _name text
  , _description text
  , _acquisition_date date
  , _expiration_date date
  , _unit_cost numeric
  , _unit_price numeric
  , _quantity_available integer
  , _quantity_sold integer
  , _additional_notes text
)
RETURNS void AS $$
BEGIN
    WITH insert_meta AS (
        INSERT INTO meta (metakey, created_by, additional_notes)
        VALUES (gen_metakey('item'), 1, _additional_notes)
        RETURNING id
    )
    INSERT INTO items (
        sku
      , upc
      , name
      , description
      , acquisition_date
      , expiration_date
      , unit_cost
      , unit_price
      , quantity_available
      , quantity_sold
      , meta_id
    )
    VALUES (
        _sku
      , _upc
      , _name
      , _description
      , _acquisition_date
      , _expiration_date
      , _unit_cost
      , _unit_price
      , _quantity_available
      , _quantity_sold
      , (SELECT id FROM insert_meta)
    );
END;
$$ LANGUAGE plpgsql;
