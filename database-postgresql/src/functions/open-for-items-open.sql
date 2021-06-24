CREATE FUNCTION open_for_items_open(_sku text)
RETURNS void AS $$
BEGIN
    UPDATE meta SET opens = opens + 1
    WHERE id = (
        SELECT meta_id
        FROM items
        WHERE sku = _sku
    );
END;
$$ LANGUAGE plpgsql;
