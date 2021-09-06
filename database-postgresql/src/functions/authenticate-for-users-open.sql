CREATE FUNCTION authenticate_for_users_open(_username text, _password text)
RETURNS text AS $$
DECLARE
    maybe_metakey text;
BEGIN
    SELECT metakey
    INTO maybe_metakey
    FROM users_open
    WHERE username = _username
    AND   password = encrypt(_password);

    -- IF maybe_metakey THEN
    --     ...;
    -- END IF;

    RETURN maybe_metakey;
END;
$$ LANGUAGE plpgsql;
