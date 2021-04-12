CREATE FUNCTION wrap(source text)
RETURNS text AS $$
DECLARE
    pilcrow text := CHR(182);
BEGIN
    RETURN regexp_replace(source, E'[\\n\\r]+', ' ' || pilcrow || ' ', 'g' );
END;
$$ LANGUAGE plpgsql;
