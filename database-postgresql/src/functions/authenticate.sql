CREATE FUNCTION authenticate(_username text, _password text)
RETURNS text AS $$
BEGIN
    metakey_if_authenticated := (
        SELECT metakey FROM users_open
        WHERE username = _username
        AND   password = sha1_encrypt(_password)
    );
    RETURN metakey_if_authenticated;
END;
$$ LANGUAGE plpgsql;

-- TODO: the if-else will increment either successful_logins field or failed_logins field on users table
