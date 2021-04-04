CREATE FUNCTION hard_delete_for_items_with_meta(
    _key text
)
RETURNS void
AS $$
BEGIN
    WITH delete_items AS (
        DELETE FROM items
        WHERE key=_key
        RETURNING meta_id
    )
    DELETE FROM meta
    WHERE id=(
        SELECT meta_id
        FROM items
        WHERE key=_key
    );
END;
$$ LANGUAGE plpgsql;
