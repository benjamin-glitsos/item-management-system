CREATE FUNCTION abbreviate_multiline_text(source text)
RETURNS text
AS $$
DECLARE
    max_length integer := 75;
BEGIN
    RETURN truncate(max_length, wrap(source));
END;
$$ LANGUAGE plpgsql;
