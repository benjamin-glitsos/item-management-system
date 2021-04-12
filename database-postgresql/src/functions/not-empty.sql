CREATE FUNCTION not_empty(value text)
RETURNS boolean AS $$
BEGIN
    RETURN trim(value) <> '';
END;
$$ LANGUAGE plpgsql;
