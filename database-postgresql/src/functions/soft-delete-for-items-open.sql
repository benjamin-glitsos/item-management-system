CREATE FUNCTION soft_delete_for_items_open(_sku text)
RETURNS void AS $$
BEGIN
    UPDATE meta
    SET is_deleted = true
      , deleted_at = NOW()
      , deleted_by = 1
      , edits      = edits + 1
    WHERE id = (
        SELECT meta_id
        FROM items
        WHERE sku = _sku
    );
END;
$$ LANGUAGE plpgsql;
