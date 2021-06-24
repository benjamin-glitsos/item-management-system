CREATE FUNCTION hard_delete_for_items_open(_sku text)
RETURNS void AS $$
BEGIN
    WITH delete_items AS (
        DELETE FROM items
        WHERE sku = _sku
        RETURNING meta_id
    )
    DELETE FROM meta
    WHERE id = (
        SELECT meta_id
        FROM items
        WHERE sku = _sku
    );
END;
$$ LANGUAGE plpgsql;
