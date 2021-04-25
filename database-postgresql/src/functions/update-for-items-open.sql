CREATE FUNCTION update_for_items_open(
    old_key text
  , new_key text
  , new_name text
  , new_description text
  , new_additional_notes text
)
RETURNS void AS $$
BEGIN
    WITH items_update AS (
        UPDATE items
        SET key=new_key
        , name=new_name
        , description=new_description
        WHERE key=old_key
        RETURNING meta_id
    )
    UPDATE meta
    SET edited_at=NOW(), additional_notes=new_additional_notes, edits=edits + 1
    WHERE id=(SELECT meta_id FROM items_update);
END;
$$ LANGUAGE plpgsql;
