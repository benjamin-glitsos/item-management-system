CREATE FUNCTION hard_delete_for_users_open(_username text)
RETURNS void AS $$
BEGIN
    WITH delete_users AS (
        DELETE FROM users
        WHERE username = _username
        RETURNING meta_id
    )
    DELETE FROM meta
    WHERE id = (
        SELECT meta_id
        FROM users
        WHERE username = _username
    );
END;
$$ LANGUAGE plpgsql;
