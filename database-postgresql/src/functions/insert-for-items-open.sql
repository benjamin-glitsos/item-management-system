CREATE FUNCTION insert_for_items_open(
    _sku text
  , _name text
  , _description text
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
      , name
      , description
      , meta_id
    )
    VALUES (
        _sku
      , _name
      , _description
      , (SELECT id FROM insert_meta)
    );
END;
$$ LANGUAGE plpgsql;
