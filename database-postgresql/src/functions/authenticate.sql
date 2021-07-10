CREATE FUNCTION authenticate(_username text, _password text)
RETURNS boolean AS $$
SELECT EXISTS (
    SELECT true FROM users
    WHERE username = _username
    AND   password = sha1_encrypt(_password)
)
FROM users;
$$ LANGUAGE sql;
