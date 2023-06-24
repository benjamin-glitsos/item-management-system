CREATE FUNCTION wrap(source text)
RETURNS text AS $$
DECLARE
    linebreak_symbol text := '/';
BEGIN
    RETURN regexp_replace(source, E'[\\n\\r]+', ' ' || linebreak_symbol || ' ', 'g' );
END;
$$ LANGUAGE plpgsql;
