CREATE FUNCTION restore_delete_for_items_with_meta(_key text)
RETURNS void AS $$
BEGIN
    UPDATE meta SET is_deleted=false, edits=edits + 1
    WHERE id=(
        SELECT meta_id
        FROM users
        WHERE key=_key
    );
END;
$$ LANGUAGE plpgsql;
