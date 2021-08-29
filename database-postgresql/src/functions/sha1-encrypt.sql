CREATE FUNCTION sha1_encrypt(s text)
RETURNS text AS $$
    SELECT crypt(s, gen_salt('bf'));
$$ LANGUAGE SQL STRICT IMMUTABLE;
