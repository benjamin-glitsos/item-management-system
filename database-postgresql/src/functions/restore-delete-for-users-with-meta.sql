CREATE FUNCTION restore_delete_for_users_with_meta(
    _username text
)
RETURNS void
AS $$
BEGIN
    UPDATE meta SET is_deleted=no, edits=edits + 1
    WHERE id=(
        SELECT meta_id
        FROM users
        WHERE username=_username
    );
END;
$$ LANGUAGE plpgsql;
