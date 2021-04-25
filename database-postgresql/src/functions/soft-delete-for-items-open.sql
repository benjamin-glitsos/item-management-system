CREATE FUNCTION soft_delete_for_items_open(_key text)
RETURNS void AS $$
BEGIN
    UPDATE meta SET is_deleted=true, deleted_at=NOW(), edits=edits + 1
    WHERE id=(
        SELECT meta_id
        FROM items
        WHERE key=_key
    );
END;
$$ LANGUAGE plpgsql;
