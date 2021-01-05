CREATE FUNCTION sha1_encrypt(text)
RETURNS text AS $$
    SELECT encode(digest($1, 'sha1'), 'hex')
$$ LANGUAGE SQL STRICT IMMUTABLE;
