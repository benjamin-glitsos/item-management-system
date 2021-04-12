CREATE FUNCTION abbreviate_multiline_text(source text)
RETURNS text AS $$
DECLARE
    max_length integer := 50;
BEGIN
    RETURN truncate(max_length, wrap(trim(source)));
END;
$$ LANGUAGE plpgsql;
