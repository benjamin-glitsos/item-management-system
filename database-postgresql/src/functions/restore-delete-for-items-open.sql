CREATE FUNCTION restore_delete_for_items_open(_sku text)
RETURNS void AS $$
BEGIN
    UPDATE meta SET is_deleted = false, edits = edits + 1
    WHERE id = (
        SELECT meta_id
        FROM users
        WHERE sku = _sku
    );
END;
$$ LANGUAGE plpgsql;
