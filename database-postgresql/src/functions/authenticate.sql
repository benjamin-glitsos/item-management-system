CREATE FUNCTION authenticate(_username text, _password text)
RETURNS boolean AS $$
SELECT EXISTS (
    SELECT true FROM users
    WHERE username = _username
    AND   password = sha1_encrypt(_password)
)
FROM users;
$$ LANGUAGE sql;

-- TODO: change it to psql language. Use RETURNS query
-- TODO: return the metakey from users_open and then in the doobie you will need to use ".option" instead of ".unique"
-- TODO: the if-else will increment either successful_logins field or failed_logins field on users table
