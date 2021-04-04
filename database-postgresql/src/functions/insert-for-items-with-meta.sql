CREATE FUNCTION insert_for_items_with_meta(
    _key text
  , _name text
  , _description text
  , _notes text
)
RETURNS void
AS $$
BEGIN
    WITH insert_meta AS (
        INSERT INTO meta (metakey, notes)
        VALUES (generate_random_metakey('users'), _notes)
        RETURNING id
    )
    INSERT INTO items (
        key
      , description
      , meta_id
    )
    VALUES (
        _key
      , _description
      , (SELECT id FROM insert_meta)
    );
END;
$$ LANGUAGE plpgsql;
