CREATE FUNCTION authenticate_for_users_open(_username text, _password text)
RETURNS text AS $$
DECLARE
    maybe_metakey text;
BEGIN
    SELECT metakey
    INTO maybe_metakey
    FROM users_open
    WHERE username = _username
    AND   password = sha1_encrypt(_password);

    RETURN maybe_metakey;
END;
$$ LANGUAGE plpgsql;

-- TODO: the if-else will increment either successful_logins field or failed_logins field on users table
