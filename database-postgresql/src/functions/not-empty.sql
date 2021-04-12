CREATE FUNCTION not_empty(
    s text
)
RETURNS boolean AS $$
BEGIN
    RETURN trim(s) <> '';
END;
$$ LANGUAGE plpgsql;
