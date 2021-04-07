CREATE FUNCTION abbreviate_multiline_text(source text)
RETURNS text
AS $$
DECLARE
    max_length := 50;
BEGIN
    RETURN truncate(max_length, wrap(description))
END;
$$ LANGUAGE plpgsql;
