CREATE FUNCTION open_for_items_with_meta(
    _key text
)
RETURNS void AS $$
BEGIN
    UPDATE meta SET opens=opens + 1
    WHERE id=(
        SELECT meta_id
        FROM items
        WHERE key=_key
    );
END;
$$ LANGUAGE plpgsql;
