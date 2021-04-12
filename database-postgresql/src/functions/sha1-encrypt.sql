CREATE FUNCTION sha1_encrypt(secret text)
RETURNS text AS $$
    SELECT encode(digest(secret, 'sha1'), 'hex')
$$ LANGUAGE SQL STRICT IMMUTABLE;
